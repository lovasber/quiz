package com.company;

import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {
    private Modell modell;
    private View view;

    public Controller() {
        modell = new Modell();
        view = new View(this);
    }

    /**
     * A függvény ellenőrzi, hogy a felhasználó helyes jelszót adott e meg. Ezt úgy teszi, hogy
     * ugyanazzal a titkosítási módszerrel titkosítja a user által begépelt jelszót és összeveti az adatbázisban tárolttal.
     * @param fnev
     * @param jelszo
     */
    public boolean helyesJelszoE(String fnev, String jelszo){
        boolean helyesjelszo = false;
        String SQL_JELSZO = "SELECT jelszo FROM felhasznalok WHERE felhasznaloNev='"+fnev+"' AND jelszo=+'"+titkosit(jelszo)+"';";
        try {
            Statement stFelhasznalok = modell.getCON().createStatement();
            ResultSet rs = stFelhasznalok.executeQuery(SQL_JELSZO);
            if (rs.next() && rs.getString(1).length()!=0){
                //System.out.println(String.valueOf(rs.getString(1)));
                //String abJelszo = rs.getString(1);
                helyesjelszo = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return helyesjelszo;
    }


    /**
     * A bejelentkezés folyamata. Itt jön létre a Felhasználó objektum.
     * @param fnev
     */
    public void bejelentkezesFolyamat(String fnev) {
        String SQL_JELSZO = "SELECT id FROM felhasznalok WHERE felhasznalonev='"+ fnev +"';";
        int felhasznaloId = -1;
        try {
            Statement stFelhasznalok = modell.getCON().createStatement();
            ResultSet rs = stFelhasznalok.executeQuery(SQL_JELSZO);
            while (rs.next()){
                felhasznaloId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        modell.setFelhasznalo(felhasznaloBetolt(felhasznaloId));
        int felhasznaloTipus = felhasznaloBetolt(felhasznaloId).getTipus();

        switch (felhasznaloTipus){
            case 0:
                view.getJt().addTab("Quiz",new DiakView(this));
                break;
            case 1:
                view.getJt().addTab("Quiz",new TanarView(this));
                break;
            case 2:
                view.getJt().addTab("Quiz",new AdminView(this));
                break;
                default:
                    System.out.println("Nincs ilyen típus!");
                    break;
        }
        view.getJt().addTab("Szótár",view.szotarFul());
        felhasznaloPanelBetolt(view.getJt());
    }

    public void felhasznaloPanelBetolt(JTabbedPane jt) {

        JPanel felh = new JPanel();
        felh.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Felhasznalo felhasznalo = felhasznaloGetter();

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String fnevS = "Felhasnzáló név: "+felhasznalo.getFelhasznalonev();
        JLabel jlNev = new JLabel(fnevS);
        String fszintS = "Felhasználó szintje: "+felhasznalo.getSzint();
        JLabel fszintL = new JLabel(fszintS);
        JButton kijelentezB = new JButton("Kijelentkezés");
        JButton statisztikaB = new JButton("Statisztika");
        gbc.weighty = 1;
        felh.add(fszintL,gbc);
        felh.add(jlNev,gbc);
        felh.add(kijelentezB,gbc);
        felh.add(statisztikaB,gbc);
        jt.addTab("Felhasználó",felh);
    }

    /**
     * Ellenőrzi, hogy az adatbázisban található-e a felhasználó által begépelt felhasználó név.
     * @param fnev
     * @return
     */
    public boolean letezikEfelhasznalo(String fnev){
        String SQL_FELHASZNALOK = "SELECT COUNT(felhasznaloNev) FROM felhasznalok WHERE felhasznaloNev='"+fnev+"';";
        boolean letezik = false;
        try {
            Statement stFelhasznalok = modell.getCON().createStatement();
            ResultSet rs = stFelhasznalok.executeQuery(SQL_FELHASZNALOK);
            if (rs.next() && rs.getInt(1)!=0){
                letezik = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return letezik;
    }

    private char[] titkosit(char[] titkositando){
        char[] titkostitott=titkositando;

        return titkostitott;
    }

    /**
     * Ez a metódus létrehozza a felhasználókat az adatbázisban
     * @param felhasznalonev
     * @param teljesNev
     * @param jelszo
     */
    public void diakRegisztracio(String felhasznalonev, String teljesNev, String jelszo){
        String SQL_UJFELHASZNALOT_LETREHOZZ = "INSERT INTO `felhasznalok` (`id`, `felhasznaloNev`, `teljesNev`, `jelszo`, `szint`, `tipus`, `joValaszDb`, `rosszValaszDb`) " +
                "VALUES ( NULL , '" + felhasznalonev + "', '" + teljesNev + "','" + jelszo + "', '1', '0', '0', '0')";
        try {
            Statement stm = modell.getCON().createStatement();
            stm.executeUpdate(SQL_UJFELHASZNALOT_LETREHOZZ);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Két String típusu változót vet össze a függvény, megnézi, hogy egyeznek-e.
     * @param jelszo
     * @param jelszomeg
     * @return
     */
    public boolean egyezoJelszo(String jelszo, String jelszomeg){
        return jelszo.equals(jelszomeg)?true:false;
    }

    /**
     * A függvény ellenőrzi hogy a paraméterben átvett felhasználónév létezik e az adatbázisban és az ennek megfelelő értéket adja vissza.
     * @param felhasznaloNev
     * @return
     */
    public boolean szabadFelhasznaloNev(String felhasznaloNev){
        boolean szabad = true;
        String SQL_FELHASZNALOK = "SELECT felhasznaloNev FROM felhasznalok";

        try {
            Statement stFelhasznalok = modell.getCON().createStatement();
            ResultSet rs = stFelhasznalok.executeQuery(SQL_FELHASZNALOK);
            while (rs.next()){
                if (felhasznaloNev.equals(rs.getString(1))){
                    szabad=false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return szabad;
    }

    /**
     * MD5 alapú titkosítást végez a függvény a paraméterben átvett Stringen és visszatlér a titkosított megfelelőjével.
     * @param titkositando
     * @return
     */
    public String titkosit(String titkositando){
        String titkositott="";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(titkositando.getBytes());
            byte[] b = md.digest();
            StringBuffer sb =  new StringBuffer();
            for(byte b1 : b) {
                sb.append(Integer.toHexString(b1 & 0xff).toString());
            }
            titkositott = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return titkositott;
    }

    /**
     * A felhasználó id-ja alapján lekéri az adatokat az adatbázisból és a függvény visszatér egy Felhasznalo objektummal.
     * @param felhasznId
     * @return
     */
    private Felhasznalo felhasznaloBetolt(int felhasznId) {
            Felhasznalo felhasznalo = null;
            String SQL_FELHASZNALOIADATOK = "SELECT * FROM felhasznalok WHERE id='"+felhasznId+"';";
            try{
                Statement st = modell.getCON().createStatement();
                ResultSet rs = st.executeQuery(SQL_FELHASZNALOIADATOK);
                while(rs.next()){
                    System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getInt(5)+" "+rs.getInt(6)+" "+rs.getInt(7)+" "+rs.getInt(8));
                    felhasznalo=new Felhasznalo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getInt(8));
                }
            }catch (SQLException e){
               e.printStackTrace();
            }
            return felhasznalo;
    }

    /**
     * Felhasználót ad vissza.
     * @return
     */
    public Felhasznalo felhasznaloGetter(){
        return modell.getFelhasznalo();
    }

    public void ujablakmegynit(JFrame fr){
        fr.setVisible(true);
    }



}
