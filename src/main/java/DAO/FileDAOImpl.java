package DAO;

import Model.*;
import lombok.AllArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class FileDAOImpl implements FileDAO<Entity> {

    private File file;
    private String separator;

    @Override
    public void save(List<Entity> models) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            try {
                int i = 0;
                for(Entity entity: models){
                    bufferedWriter.write(entity.toFileString(separator));
                    if(i++ != models.size()-1)
                        bufferedWriter.newLine();
                }
            }
            finally {
                bufferedWriter.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Optional> findAll() {
        List<Optional> items = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            try {
                String line;
                while ((line=bufferedReader.readLine())!=null){
                    String[] params = line.split(separator);
                    Entity entity;
                    switch (file.getName()){
                        case "Магазин.txt":{
                            entity = new Shop(params);
                            break;
                        }
                        case "Продавцы.txt":{
                            entity = new Seller(params);
                            break;
                        }
                        case "Регистрация.txt":{
                            entity = new Registration(params);
                            break;
                        }
                        case "Поставщики.txt":{
                            entity = new Supplier(params);
                            break;
                        }
                        default:{
                            entity = null;
                        }
                    }
                    items.add(Optional.of(entity));
                }
            }
            finally{
                bufferedReader.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return items;
    }
}