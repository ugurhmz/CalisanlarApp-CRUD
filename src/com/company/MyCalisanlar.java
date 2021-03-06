package com.company;


import com.company.model.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MyCalisanlar {
    private Connection con = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;

    //CONSTRUCTOR
    public MyCalisanlar(){
        String url = "jdbc:mysql://localhost:"+ DatabaseConnection.dbPort+"/"+DatabaseConnection.dbName+"?useUnicode=true&characterEncoding=utf8";

        try{
            con = DriverManager.getConnection(url,DatabaseConnection.userName,DatabaseConnection.password);
            System.out.println("Db bağlantı başarılı...");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    // ÇALIŞAN EKLE
    public void calisanEkle(String ad, String soyad, String departman, String maas){
        String sorgu = "INSERT INTO calisanlar (ad,soyad,departman,maas) VALUES(?,?,?,?)";

        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3,departman);
            preparedStatement.setString(4, maas);

            preparedStatement.executeUpdate();


        } catch (SQLException ex) {
            Logger.getLogger(MyCalisanlar.class.getName()).log(Level.SEVERE, null, ex);
        }


    }


    // ÇALIŞAN GÜNCELLE
    public void calisanGuncelle(int id, String ad, String soyad, String departman, String maas){
        String sorgu = "Update calisanlar Set ad=?, soyad=?, departman=?, maas=? where id=?";

        try {
            preparedStatement = con.prepareStatement(sorgu);

            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, departman);
            preparedStatement.setString(4, maas);

            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(MyCalisanlar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ÇALIŞAN SİL
    public void calisanSil(int id) {
        String sorgu = "DELETE FROM calisanlar where id=?";

        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setInt(1,id);

            preparedStatement.executeUpdate();


        } catch (SQLException ex) {
            Logger.getLogger(MyCalisanlar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    // GİRİS YAP
    public boolean girisYap(String kullanici_adi, String parola) {
        String sorgu ="Select * from adminler where username = ? and password = ?";

        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setString(1, kullanici_adi);
            preparedStatement.setString(2,parola);

            ResultSet rs = preparedStatement.executeQuery();

            return rs.next();


        } catch (SQLException ex) {
            Logger.getLogger(MyCalisanlar.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }


    }


    // ÇALIŞANLARI GETİR
    public ArrayList<Calisan> calisanlariGetir() {

        ArrayList<Calisan> cikti = new ArrayList<>();

        try {
            statement = con.createStatement();
            String sorgu = "Select * FROM calisanlar";
            ResultSet rs = statement.executeQuery(sorgu);

            while(rs.next()){
                int id = rs.getInt("id");
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String dep = rs.getString("departman");
                String maas = rs.getString("maas");

                cikti.add(new Calisan(id,ad,soyad,dep,maas));

            }


            return cikti;

        } catch (SQLException ex) {
            Logger.getLogger(MyCalisanlar.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        }
    }
}
