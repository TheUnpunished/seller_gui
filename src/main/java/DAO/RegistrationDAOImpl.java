package DAO;

import Model.Registration;
import Util.DbWork;
import lombok.AllArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
public class RegistrationDAOImpl implements RegistrationDAO {
    private DbWork db;
    @Override
    public Optional<Registration> find(Registration model) {
        String sql = "select * from Регистрация where Имя = ?";
        try{
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(1,model.getName());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Registration registration = new Registration(rs.getString("Имя"),rs.getDate("Дата_регистрации"),
                        rs.getInt("Количество_позиций"));
                return Optional.of(registration);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Registration> findAll() {
        List<Registration> registrations = new ArrayList<>();
        String sql = "select * from Регистрация";
        try {
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Registration registration = new Registration(rs.getString("Имя"),rs.getDate("Дата_регистрации"),
                        rs.getInt("Количество_позиций"));
                registrations.add(registration);
            }
            return registrations;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Registration model) {
        String sql = "delete from Регистрация where Имя = ?";
        try{
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(1, model.getName());
            ps.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Registration model) {
        String sql = "update Регистрация set Дата_регистрации = ?, Количество_позиций = ? where Имя = ?";
        try{
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setDate(1,model.getDate());
            ps.setInt(2,model.getCount());
            ps.setString(3,model.getName());
            ps.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void save(Registration model) {
        String sql = "insert into Регистрация values (?,?,?)";
        try{
            db.setConnection();
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setString(1,model.getName());
            ps.setDate(2,model.getDate());
            ps.setInt(3,model.getCount());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
