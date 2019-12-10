package DAO;

import Model.Supplier;
import Util.DbWork;
import lombok.AllArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class SupplierDAOImpl implements SupplierDAO {
    private DbWork db;
    @Override
    public Optional<Supplier> find(Supplier model) {
        String sql = "select * from Поставщик where Тип = ?";
        try{
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(1, model.getType());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Supplier supplier = new Supplier(rs.getString("Тип"),rs.getString("Поставщик"));
                return Optional.of(supplier);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public List<Supplier> findAll() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "select * from Поставщик";
        try{
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Supplier supplier = new Supplier(rs.getString("Тип"),rs.getString("Поставщик"));
                suppliers.add(supplier);
            }
            return suppliers;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Supplier model) {
        String sql = "delete from Поставщик where Тип = ?";
        try{
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(1,model.getType());
            ps.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Supplier model) {
        String sql = "update Поставщик set Поставщик = ? where Тип = ?";
        try{
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(2,model.getType());
            ps.setString(1,model.getName());
            ps.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void save(Supplier model) {
        String sql = "insert into Поставщик values (?,?)";
        try {
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(1,model.getType());
            ps.setString(2,model.getName());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
