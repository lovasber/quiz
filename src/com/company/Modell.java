package com.company;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class Modell implements AdatbazisKapcsolat {

    private Connection CON;
    private Felhasznalo felhasznalo;




    public Modell() {
        adatbazisKapcsolatLetrehoz();
    }

    private void adatbazisKapcsolatLetrehoz() {
        try{
            Class.forName(ABDRIVER);
            CON = DriverManager.getConnection(ABURL,ABFELHASZNALO,"");
            Statement ST = CON.createStatement();
        }catch(Exception e){
            System.out.println("Adatbázis kapcsolódási hiba: "+e.getLocalizedMessage());
        }
        System.out.println("sikeres adatbázis kapcsolat");
    }


    public Connection getCON() {
        return CON;
    }

    public void setFelhasznalo(Felhasznalo felhasznalo) {
        this.felhasznalo = felhasznalo;
    }

    public Felhasznalo getFelhasznalo() {
        return felhasznalo;
    }

    public boolean torolhetoEAdmin() throws SQLException {
        boolean torolheto=false;
        int adminDb=0;
        String SQL_COUNTADMIN = "SELECT COUNT(id) FROM felhasznalok WHERE tipus=2";

        PreparedStatement ps = CON.prepareStatement(SQL_COUNTADMIN);
        ResultSet res = ps.executeQuery();
        while (res.next()){
            adminDb = res.getInt(1);
        }
        if (adminDb>1){
            torolheto=true;
        }
        System.out.println("Törölhető admindb: "+adminDb);
        return torolheto;
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
            Statement stFelhasznalok = CON.createStatement();
            ResultSet rs = stFelhasznalok.executeQuery(SQL_FELHASZNALOK);
            if (rs.next() && rs.getInt(1)!=0){
                letezik = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return letezik;
    }


    public void naplozas(int id) throws SQLException {
        String SQL_NAPLO = "INSERT INTO `naplo` (`bejelentkezesdatum`, `felhasznid`) VALUES (CURRENT_TIMESTAMP, '"+id+"')";

        Statement st = getCON().createStatement();
        st.executeUpdate(SQL_NAPLO);
    }


    public void kerdesSzerk(String kerdesSzov,String valasz,String valaszLehet,int pontszam,int id) throws SQLException {
        String SQL_KERDESUPDATE = "UPDATE `kerdes` SET kerdesSzovege = ? , valasz= ? , valaszlehetosegek=? , pontszam = ? WHERE `kerdes`.`id`  = ?;";
        PreparedStatement ps = getCON().prepareStatement(SQL_KERDESUPDATE);
        ps.setString(1, kerdesSzov);
        ps.setString(2, valasz);
        ps.setString(3, valaszLehet);
        ps.setInt(4, pontszam);
        ps.setInt(5, id);
        ps .executeUpdate();
    }

    public Modell getModell() {
        return this;
    }

    public ArrayList<ArrayList<String>> diakEredmenyLista() throws SQLException {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        String SQL_DIAKERED = "SELECT * FROM diakered ORDER BY fokatid,alkatid,diakid";

        Statement st = getCON().createStatement();
        ResultSet res = st.executeQuery(SQL_DIAKERED);

        while (res.next()){
            ArrayList<String> allist = new ArrayList<>();
            allist.add(res.getString(1));
            allist.add(res.getString(2));
            allist.add(res.getString(3));
            allist.add(res.getString(4));
            allist.add(res.getString(5));
            allist.add(res.getString(6));
            allist.add(res.getString(7));
            list.add(allist);
        }

        return list;
    }

    public void kerdesTorol(int kerdesId) throws SQLException {
        String SQL_KERDESTOROL = "DELETE FROM kerdes WHERE id="+kerdesId+";";

        Statement st = getCON().createStatement();
        st.executeUpdate(SQL_KERDESTOROL);
    }

    public String diakNev(int diakId) throws SQLException {
        String diaknev = "";
        String SQL_DIAKIDTODIAKNEV = "SELECT teljesnev FROM felhasznalok WHERE id='"+diakId+"'";
        Statement st = getCON().createStatement();

        ResultSet res = st.executeQuery(SQL_DIAKIDTODIAKNEV);
        while(res.next()){
            diaknev=res.getString(1);
        }
        return diaknev;
    }

    public void diakEredFelvisz(int diakid, String fokat, String alkat, String kerdesSorrend, String elertPontszamSorrend, String diakValaszai){
        String SQL_DIAKEREDINSERT = "INSERT INTO `diakered` (`diakid`, `fokatid`, `alkatid`, `kerdesidsorrend`, `elertpontszamsorrend`, `diakvalaszai`, `beadasidopont`)" +
                " VALUES (?, ?, ?, ?, ?, ?,CURRENT_TIMESTAMP)";
        try {
            //Statement st = getCON().createStatement();
            //st.executeUpdate(SQL_DIAKEREDINSERT);
            PreparedStatement ps =getCON().prepareStatement(SQL_DIAKEREDINSERT);
            ps.setInt(1,diakid);
            ps.setString(2,fokat);
            ps.setString(3,alkat);
            ps.setString(4,kerdesSorrend);
            ps.setString(5,elertPontszamSorrend);
            ps.setString(6,diakValaszai);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Frissíti az adatbázisban lévő felhasználói adatokat.
     * @param aktiv
     * @param tipus
     * @param id
     */
    public void felhasznaloFrissit(int aktiv,int tipus,int id) throws SQLException {

        String SQL_FELHASZNFRISSIT = "UPDATE felhasznalok SET aktiv= ?, tipus=? WHERE id=?";
        try {
            PreparedStatement ps = getCON().prepareStatement(SQL_FELHASZNFRISSIT);
            ps.setInt(1,aktiv);
            ps.setInt(2,tipus);
            ps.setInt(3,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Kerdes> alkatKerdesei(String alkat){
        ArrayList<Kerdes> list = new ArrayList<>();
        String SQL_ALKATKERDESEI = "SELECT * FROM kerdes WHERE alKategoria=?";
        try {
            PreparedStatement ps = getCON().prepareStatement(SQL_ALKATKERDESEI);
            ps.setString(1,alkat);
            ResultSet res = ps.executeQuery();
            while (res.next()){
                list.add(new Kerdes(res.getInt(1),res.getString(2),res.getString(3),res.getInt(4),res.getString(5),res.getString(6),res.getString(7),res.getInt(8)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }


    public ArrayList<Felhasznalo> felhasznalokLista(){
        ArrayList<Felhasznalo> felhasznL = new ArrayList<>();

        String SQL_FELHASZNALOK = "SELECT * FROM felhasznalok";

        try {
            Statement st = getCON().createStatement();
            ResultSet rs = st.executeQuery(SQL_FELHASZNALOK);
            while (rs.next()){
                felhasznL.add(new Felhasznalo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getInt(9)));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return felhasznL;
    }

    /**
     * Új kérdést hoz létre az adatbázisban
     * @param foKategoria
     * @param alKategoria
     * @param tipus
     * @param kerdesSzovege
     * @param valasz
     * @param valaszlehetosegek
     * @param pontszam
     */
    public void kerdesLetrehoz(String foKategoria, String alKategoria, int tipus, String kerdesSzovege, String valasz, String valaszlehetosegek, int pontszam){
        String SQL_KERDESLETREHOZ = "INSERT INTO `kerdes` (`id`, `foKategoria`, `alKategoria`, `tipus`, `kerdesSzovege`, `valasz`, `valaszlehetosegek`, `pontszam`) " +
                "VALUES (NULL, ?, ?,?, ?, ?, ?,?)";
        try {
            PreparedStatement ps = getCON().prepareStatement(SQL_KERDESLETREHOZ);
            ps.setString(1,foKategoria);
            ps.setString(2,alKategoria);
            ps.setInt(3,tipus);
            ps.setString(4,kerdesSzovege);
            ps.setString(5,valasz);
            ps.setString(6,valaszlehetosegek);
            ps.setInt(7,pontszam);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Az összes eddig létrhozott kategóriát adja vissza egy ArrayList ben
     * @return
     */
    public ArrayList<String> letezoFoKategoriak() {
        ArrayList<String> katList = new ArrayList<>();
        String SQL_KATEGORIAK = "SELECT katAzon FROM fokategoriamagyarazat";
        try {
            Statement stm = getCON().createStatement();
            ResultSet res = stm.executeQuery(SQL_KATEGORIAK);
            while (res.next()){
                katList.add(res.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return katList;
    }


    /**
     * Az összes eddig létrhozott kategóriát adja vissza egy ArrayList ben
     * @return
     */
    public ArrayList<String> letezoAlKategoriak() {
        ArrayList<String> katList = new ArrayList<>();
        String SQL_KATEGORIAK = "SELECT katAzon FROM alkategoriamagyarazat";
        Statement stm = null;
        try {
            stm = getCON().createStatement();
            ResultSet res = stm.executeQuery(SQL_KATEGORIAK);
            while (res.next()){
                katList.add(res.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return katList;
    }

    /**
     *Új főkategóriát hoz létre az adatbázisban
     * @param katnev
     */
    public void fokatLetrehoz(String katnev,String katleiras){
        String SQL_KATBESZUR = "INSERT into fokategoriamagyarazat VALUES(?,?)";

        try {
            PreparedStatement ps = getCON().prepareStatement(SQL_KATBESZUR);
            ps.setString(1,katnev);
            ps.setString(2,katleiras);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Alkategóriát hoz létre.
     * @param katnev
     * @param fokat
     * @param katleiras
     */
    public void alkatLetrehoz(String katnev,String fokat,String katleiras){
        String SQL_ALKATBESZUR = "INSERT INTO `alkategoriamagyarazat` (`katAzon`, `fokatid`, `katLeiras`, `pelda`) VALUES (?, ?, ?, NULL)";

        try {
            PreparedStatement ps = getCON().prepareStatement(SQL_ALKATBESZUR);
            ps.setString(1,katnev);
            ps.setString(2,fokat);
            ps.setString(3,katleiras);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Az adott főkategóriához tartozó alkategóriákat adja vissza.
     * @param fokat
     * @return
     */
    public ArrayList<String> fokatAlkategoriai(String fokat){
        ArrayList<String> list = new ArrayList<>();
        String SQL_KATAZON = "SELECT katAzon FROM alkategoriamagyarazat WHERE fokatid='"+fokat+"'";
        try{
            Statement st = getCON().createStatement();
            ResultSet res = st.executeQuery(SQL_KATAZON);
            while(res.next()){
                list.add(res.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Visszaadja a létező kérdéseket egy listában
     * @return
     */
    public ArrayList<Kerdes> letezoKerdesek(){
        ArrayList<Kerdes> list = new ArrayList<>();
        String SQL_KERDESEK = "SELECT * FROM kerdes ORDER BY alKategoria ASC";

        try{
            Statement st = getCON().createStatement();
            ResultSet res = st.executeQuery(SQL_KERDESEK);
            while (res.next()){
                list.add(new Kerdes(res.getInt(1),res.getString(2),res.getString(3),res.getInt(4),res.getString(5),
                        res.getString(6),res.getString(7),res.getInt(8)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * A felhasználó id-ja alapján lekéri az adatokat az adatbázisból és a függvény visszatér egy Felhasznalo objektummal.
     * @param felhasznId
     * @return
     */
    public Felhasznalo felhasznaloBetolt(int felhasznId) {
        Felhasznalo felhasznalo = null;
        String SQL_FELHASZNALOIADATOK = "SELECT * FROM felhasznalok WHERE id='"+felhasznId+"';";
        try{
            Statement st = getCON().createStatement();
            ResultSet rs = st.executeQuery(SQL_FELHASZNALOIADATOK);
            felhasznalo=null;
            while(rs.next()){
                System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getInt(5)+" "+rs.getInt(6)+" "+rs.getInt(7)+" "+rs.getInt(8));
                felhasznalo=new Felhasznalo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getInt(9));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return felhasznalo;
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
            Statement stFelhasznalok = getCON().createStatement();
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
     * Ez a metódus létrehozza a felhasználókat az adatbázisban
     * @param felhasznalonev
     * @param teljesNev
     * @param jelszo
     */
    public void regisztracio(String felhasznalonev, String teljesNev, String jelszo,int tipus,int aktiv){
        String SQL_UJFELHASZNALOT_LETREHOZZ = "INSERT INTO `felhasznalok` (`id`, `felhasznaloNev`, `teljesNev`, `jelszo`, `szint`, `tipus`, `joValaszDb`, `rosszValaszDb`,`aktiv`) " +
                "VALUES ( NULL , ?, ?,?, '1', ?, '0', '0',?)";
        try {
            PreparedStatement ps = getCON().prepareStatement(SQL_UJFELHASZNALOT_LETREHOZZ);
            ps.setString(1,felhasznalonev);
            ps.setString(2,teljesNev);
            ps.setString(3,jelszo);
            ps.setInt(4,tipus);
            ps.setInt(5,aktiv);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * A függvény ellenőrzi, hogy a felhasználó helyes jelszót adott e meg. Ezt úgy teszi, hogy
     * ugyanazzal a titkosítási módszerrel titkosítja a user által begépelt jelszót és összeveti az adatbázisban tárolttal.
     * @param fnev
     * @param jelszo
     */
    public boolean helyesJelszoE(String fnev, String jelszo){
        boolean helyesjelszo = false;
        String titkositott = titkosit(jelszo);
        String SQL_JELSZO = "SELECT jelszo FROM felhasznalok WHERE felhasznaloNev=? AND jelszo=?;";
        try {
            PreparedStatement ps = getCON().prepareStatement(SQL_JELSZO);
            ps.setString(1,fnev);
            ps.setString(2,titkositott);
            ResultSet rs = ps.executeQuery();
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

    public boolean aktiveE(String fnev){
        boolean aktiv = false;
        String SQL_JELSZO = "SELECT aktiv FROM felhasznalok WHERE felhasznaloNev=?;";
        try {
            PreparedStatement ps = getCON().prepareStatement(SQL_JELSZO);
            ps.setString(1,fnev);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1)==0){
                aktiv = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aktiv;
    }

    /**
     * MD5 alapú titkosítást végez a függvény a paraméterben átvett Stringen és visszatlér a titkosított megfelelőjével.
     * @param titkositando
     * @return
     */
    public String titkosit(String titkositando){
        //Forrás: https://www.youtube.com/watch?v=9eisErB4MO8&t=525s
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

    public void jelszoValtoztat(int felhId,String ujJelszo) throws SQLException {
        String SQL_JELSZOVALTOZTAT = "UPDATE `felhasznalok` SET `jelszo` = ? WHERE `felhasznalok`.`id` = ?";
        PreparedStatement ps = getCON().prepareStatement(SQL_JELSZOVALTOZTAT);
        ujJelszo = titkosit(ujJelszo);
        ps.setString(1,ujJelszo);
        ps.setInt(2,felhId);
        ps.executeUpdate();
    }

    public int felhasznaloTipusa(int id) throws SQLException {
        int tipus =-1;
        String SQL_FELHTIP = "SELECT tipus FROM felhasznalok WHERE id = ?";
        PreparedStatement ps = getCON().prepareStatement(SQL_FELHTIP);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            tipus=rs.getInt(1);
        }
        return tipus;
    }


    public int felhasznId(String fnev) {
        int felhasznaloId=-1;
        String SQL_JELSZO = "SELECT id FROM felhasznalok WHERE felhasznalonev =? ";

        try {
            PreparedStatement ps = getCON().prepareStatement(SQL_JELSZO);
            ps.setString(1,fnev);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                felhasznaloId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return felhasznaloId;
    }
}
