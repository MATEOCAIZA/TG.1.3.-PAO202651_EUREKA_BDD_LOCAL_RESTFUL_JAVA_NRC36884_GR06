/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.servicio;

import ec.edu.monster.db.AccesoDB;
import ec.edu.monster.modelo.Movimiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Isaac
 */
public class EurekaService {

    public List<Movimiento> leerMovimientos(String cuenta) {
        Connection cn = null;
        List<Movimiento> lista = new ArrayList<>();
        // CORREGIDO: TipoMovimiento y Movimiento con mayúsculas
        String sql = "SELECT "
                + " m.chr_cuencodigo cuenta, "
                + " m.int_movinumero nromov, "
                + " m.dtt_movifecha fecha, "
                + " t.vch_tipodescripcion tipo, "
                + " t.vch_tipoaccion accion, "
                + " m.dec_moviimporte importe, "
                + " m.chr_cuenreferencia referencia "
                + "FROM TipoMovimiento t "
                + "INNER JOIN Movimiento m ON t.chr_tipocodigo = m.chr_tipocodigo "
                + "WHERE m.chr_cuencodigo = ?";
        try {
            cn = AccesoDB.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Movimiento rec = new Movimiento();
                rec.setCuenta(rs.getString("cuenta"));
                rec.setNromov(rs.getInt("nromov"));
                rec.setFecha(rs.getDate("fecha"));
                rec.setTipo(rs.getString("tipo"));
                rec.setAccion(rs.getString("accion"));
                rec.setImporte(rs.getDouble("importe"));
                rec.setReferencia(rs.getString("referencia"));
                lista.add(rec);
            }
            rs.close();
            pstm.close(); // Buena práctica cerrar el statement también
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                // Manejo de excepciones
            }
        }
        return lista;
    }

    public String convertirMovimientosAJSON(List<Movimiento> movimientos) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return gson.toJson(movimientos);
    }

    public void registrarDepósito(String cuenta, double importe, String codEmp) {
        Connection cn = null;
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);
            
            // CORREGIDO: Cuenta con mayúscula
            String sql = "select dec_cuensaldo, int_cuencontmov "
                    + "from Cuenta "
                    + "where chr_cuencodigo = ? and vch_cuenestado = 'ACTIVO' "
                    + "for update ";
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) {
                throw new SQLException("ERROR, cuenta no existe, o no esta activa");
            }
            double saldo = rs.getDouble("dec_cuensaldo");
            int cont = rs.getInt("int_cuencontmov");
            rs.close();
            pstm.close();
            
            saldo += importe;
            cont++;
            
            // CORREGIDO: Cuenta con mayúscula
            sql = "update Cuenta "
                    + "set dec_cuensaldo = ?, "
                    + "int_cuencontmov = ? "
                    + "where chr_cuencodigo = ? and vch_cuenestado = 'ACTIVO'";
            pstm = cn.prepareStatement(sql);
            pstm.setDouble(1, saldo);
            pstm.setInt(2, cont);
            pstm.setString(3, cuenta);
            pstm.executeUpdate();
            pstm.close();
            
            // CORREGIDO: Movimiento con mayúscula
            sql = "insert into Movimiento(chr_cuencodigo,"
                    + "int_movinumero,dtt_movifecha,chr_emplcodigo,chr_tipocodigo,"
                    + "dec_moviimporte) values(?,?,SYSDATE(),?,'003',?)";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            pstm.setInt(2, cont);
            pstm.setString(3, codEmp);
            pstm.setDouble(4, importe);
            pstm.executeUpdate();
            pstm.close();
            
            cn.commit();
        } catch (SQLException e) {
            try {
                if (cn != null) cn.rollback();
            } catch (Exception el) {
            }
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            try {
                if (cn != null) cn.rollback();
            } catch (Exception el) {
            }
            throw new RuntimeException("ERROR, en el proceso registrar depósito, intentelo mas tarde.");
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (Exception e) {
            }
        }
    }

    public void registrarRetiro(String cuenta, double importe, String codEmp) {
        Connection cn = null;
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            // CORREGIDO: Cuenta con mayúscula
            String sql = "SELECT dec_cuensaldo, int_cuencontmov "
                    + "FROM Cuenta "
                    + "WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO' "
                    + "FOR UPDATE";
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) {
                throw new SQLException("ERROR, la cuenta no existe o no está activa.");
            }

            double saldo = rs.getDouble("dec_cuensaldo");
            int cont = rs.getInt("int_cuencontmov");
            rs.close();
            pstm.close();

            if (saldo < importe) {
                throw new SQLException("ERROR, saldo insuficiente.");
            }

            saldo -= importe;
            cont++;
            
            // CORREGIDO: Cuenta con mayúscula
            sql = "UPDATE Cuenta SET dec_cuensaldo = ?, int_cuencontmov = ? "
                    + "WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO'";
            pstm = cn.prepareStatement(sql);
            pstm.setDouble(1, saldo);
            pstm.setInt(2, cont);
            pstm.setString(3, cuenta);
            pstm.executeUpdate();
            pstm.close();

            // CORREGIDO: Movimiento con mayúscula
            sql = "INSERT INTO Movimiento(chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte) "
                    + "VALUES (?, ?, SYSDATE(), ?, '004', ?)";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            pstm.setInt(2, cont);
            pstm.setString(3, codEmp);
            pstm.setDouble(4, importe);
            pstm.executeUpdate();
            pstm.close();

            cn.commit();
        } catch (SQLException e) {
            try {
                if (cn != null) {
                    cn.rollback();
                }
            } catch (Exception ex) {
            }
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public void registrarTransferencia(String cuentaOrigen, String cuentaDestino, double importe, String codEmp) {
        Connection cn = null;
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            // CORREGIDO: Cuenta con mayúscula
            String sql = "SELECT dec_cuensaldo, int_cuencontmov "
                    + "FROM Cuenta WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO' FOR UPDATE";
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuentaOrigen);
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) {
                throw new SQLException("ERROR, la cuenta de origen no existe o no está activa.");
            }
            double saldoOrigen = rs.getDouble("dec_cuensaldo");
            int contOrigen = rs.getInt("int_cuencontmov");
            rs.close();
            pstm.close();

            if (saldoOrigen < importe) {
                throw new SQLException("ERROR, saldo insuficiente en la cuenta de origen.");
            }

            saldoOrigen -= importe;
            contOrigen++;
            
            // CORREGIDO: Cuenta con mayúscula
            sql = "UPDATE Cuenta SET dec_cuensaldo = ?, int_cuencontmov = ? WHERE chr_cuencodigo = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setDouble(1, saldoOrigen);
            pstm.setInt(2, contOrigen);
            pstm.setString(3, cuentaOrigen);
            pstm.executeUpdate();
            pstm.close();

            // CORREGIDO: Movimiento con mayúscula
            sql = "INSERT INTO Movimiento(chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte) "
                    + "VALUES (?, ?, SYSDATE(), ?, '009', ?)";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuentaOrigen);
            pstm.setInt(2, contOrigen);
            pstm.setString(3, codEmp);
            pstm.setDouble(4, importe);
            pstm.executeUpdate();
            pstm.close();

            // CORREGIDO: Cuenta con mayúscula
            sql = "SELECT dec_cuensaldo, int_cuencontmov "
                    + "FROM Cuenta WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO' FOR UPDATE";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuentaDestino);
            rs = pstm.executeQuery();
            if (!rs.next()) {
                throw new SQLException("ERROR, la cuenta de destino no existe o no está activa.");
            }
            double saldoDestino = rs.getDouble("dec_cuensaldo");
            int contDestino = rs.getInt("int_cuencontmov");
            rs.close();
            pstm.close();

            saldoDestino += importe;
            contDestino++;
            
            // CORREGIDO: Cuenta con mayúscula
            sql = "UPDATE Cuenta SET dec_cuensaldo = ?, int_cuencontmov = ? WHERE chr_cuencodigo = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setDouble(1, saldoDestino);
            pstm.setInt(2, contDestino);
            pstm.setString(3, cuentaDestino);
            pstm.executeUpdate();
            pstm.close();

            // CORREGIDO: Movimiento con mayúscula
            sql = "INSERT INTO Movimiento(chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte) "
                    + "VALUES (?, ?, SYSDATE(), ?, '008', ?)";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuentaDestino);
            pstm.setInt(2, contDestino);
            pstm.setString(3, codEmp);
            pstm.setDouble(4, importe);
            pstm.executeUpdate();
            pstm.close();

            cn.commit();
        } catch (SQLException e) {
            try {
                if (cn != null) {
                    cn.rollback();
                }
            } catch (Exception ex) {
            }
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public double consultarSaldo(String cuenta) {
        Connection cn = null;
        double saldo = -1;
        try {
            cn = AccesoDB.getConnection();

            // CORREGIDO: Cuenta con mayúscula
            String sql = "SELECT dec_cuensaldo "
                    + "FROM Cuenta "
                    + "WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO'";
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);

            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                saldo = rs.getDouble("dec_cuensaldo");
            } else {
                throw new SQLException("ERROR, la cuenta no existe o no está activa.");
            }
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
            }
        }
        return saldo;
    }
    
    public boolean login(String usuario, String clave) {
        boolean estado = false;
        Connection cn = null;

        try {
            cn = AccesoDB.getConnection();

            // MANTENIDO: Usuario ya estaba con mayúscula inicial
            String sql =
                    "SELECT COUNT(*) total "
                    + "FROM Usuario "
                    + "WHERE vch_emplusuario = ? "
                    + "AND vch_emplclave = SHA1(?) "
                    + "AND vch_emplestado = 'ACTIVO'";

            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, usuario);
            pstm.setString(2, clave);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                estado = rs.getInt("total") > 0;
            }

            rs.close();
            pstm.close();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception e) {
            }
        }
        return estado;
    }   
}