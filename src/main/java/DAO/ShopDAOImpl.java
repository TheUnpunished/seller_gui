package DAO;

import Model.Shop;
import Util.DbWork;
import lombok.AllArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ShopDAOImpl implements ShopDAO {
    private DbWork db;
    @Override
    public Optional<Shop> find(Shop model) {
        String sql = "select * from Магазин where Название = ?";
        try{
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(1,model.getName());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Shop shop = new Shop(rs.getString("Название"),rs.getString("Тип"));
                return Optional.of(shop);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public List<Shop> findAll() {
        List<Shop> shops = new ArrayList<>();
        String sql = "select * from Магазин";
        try{
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Shop shop = new Shop(rs.getString("Название"),rs.getString("Тип"));
                shops.add(shop);
            }
            return shops;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Shop model) {
        String sql = "delete from Магазин where Название = ?";
        try{
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(1,model.getName());
            ps.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void update(Shop model) {
        String sql = "update Магазин set Тип = ? where Название = ?";
        try{
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(1,model.getType());
            ps.setString(2,model.getName());
            ps.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void save(Shop model) {
        String sql = "insert into Магазин values (?,?)";
        try{
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(1,model.getName());
            ps.setString(2,model.getType());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
