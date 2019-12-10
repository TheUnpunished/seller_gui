package DAO;

import Model.Seller;
import Util.DbWork;
import lombok.AllArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class SellerDAOImpl implements SellerDAO {
    private DbWork db;
    @Override
    public Optional<Seller> find(Seller model) {
        String sql = "select * from Продавцы where Имя = ? and Город = ? and Название = ?";
        try{
           db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(1,model.getName());
            ps.setString(2,model.getCity());
            ps.setString(3,model.getProduct_name());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Seller seller = new Seller(rs.getString("Имя"),rs.getString("Город"), rs.getString("Название"),
                        rs.getInt("Цена"),rs.getInt("Количество"));
                return Optional.of(seller);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public List<Seller> findAll() {
        String sql = "select * from Продавцы";
        List<Seller> sellers = new ArrayList<>();
        try{
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Seller seller = new Seller(rs.getString("Имя"),rs.getString("Город"), rs.getString("Название"),
                        rs.getInt("Цена"),rs.getInt("Количество"));
                sellers.add(seller);
            }
            return sellers;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Seller model) {
        String sql = "delete from Продавцы where Имя = ? and Город = ? and Название = ?";
        try{
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(1,model.getName());
            ps.setString(2,model.getCity());
            ps.setString(3,model.getProduct_name());
            ps.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Seller model) {
        String sql = "update Продавцы set Цена = ?, Количество = ? where Имя = ? and Город = ? and Название = ?";
        try{
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setInt(1,model.getPrice());
            ps.setInt(2,model.getCount());
            ps.setString(3,model.getName());
            ps.setString(4,model.getCity());
            ps.setString(5,model.getProduct_name());
            ps.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void save(Seller model) {
        String sql = "insert into Продавцы values (?,?,?,?,?)";
        try{
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(1,model.getName());
            ps.setString(2,model.getCity());
            ps.setString(3,model.getProduct_name());
            ps.setInt(4,model.getPrice());
            ps.setInt(5,model.getCount());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
