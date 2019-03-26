package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {


    private Modell modell;
    private View view;
    public HashMap<String,Integer> felhtipus;

    public View getView() {
        return view;
    }

    public Controller() {
        modell = new Modell();
        view = new View(this);

        felhtipus = new HashMap<>();
        felhtipus.put("Diák",0);
        felhtipus.put("Tanár",1);
        felhtipus.put("Admin",2);
    }


    /**
     * Ellenőrzia a tesztet
     * @param kerdesek
     * @param valaszok
     * @param diakId
     */
    public void testEllenoriz(ArrayList<Kerdes> kerdesek,String[] valaszok,int diakId){
        int elertpontszam=0;
        int maxPontszam=0;

        for (int i = 0; i < kerdesek.size(); i++) {
            maxPontszam+=kerdesek.get(i).getPontszam();
        }

        String kerdesSorrend="";
        for (int j = 0; j < kerdesek.size(); j++) {
            kerdesSorrend+=kerdesek.get(j).getId()+";";
        }
        kerdesSorrend=kerdesSorrend.substring(0,kerdesSorrend.length()-1);

        String pontSzamSorrend = "";
        String[] pontSzamTomb = new String[kerdesek.size()];

        String diakValaszai = "";
        String[] diakValaszaiTomb = new String[kerdesek.size()];

        for (int k = 0; k < pontSzamTomb.length; k++) {
            pontSzamTomb[k] = "0";
        }

        for (int k = 0; k < diakValaszaiTomb.length; k++) {
            diakValaszaiTomb[k] = "nem válaszolt";
        }

        for (int i = 0; i < valaszok.length; i++) {
            String[] spl = kerdesek.get(i).getHelyesValasz().split("\\;");
            if (spl.length!=1){
                for (int j = 0; j < spl.length; j++) {
                    if (spl[j].equals(valaszok[i])){
                        pontSzamTomb[i]=kerdesek.get(i).getPontszam()+"";
                        elertpontszam+=kerdesek.get(i).getPontszam();
                    }
                }
            }
            else if (valaszok[i]!=null && valaszok[i].equals(kerdesek.get(i).getHelyesValasz())) {
                pontSzamTomb[i]=kerdesek.get(i).getPontszam() + "";
                elertpontszam+=kerdesek.get(i).getPontszam();
            }
            if (valaszok[i]!=null){
                diakValaszaiTomb[i]=valaszok[i];
            }
        }
        for (int l = 0; l < pontSzamTomb.length; l++) {
            pontSzamSorrend+=pontSzamTomb[l]+";";
        }

        for (int l = 0; l < diakValaszaiTomb.length; l++) {
            diakValaszai+=diakValaszaiTomb[l]+";";
        }

        pontSzamSorrend=pontSzamSorrend.substring(0,pontSzamSorrend.length()-1);
        diakValaszai=diakValaszai.substring(0,diakValaszai.length()-1);

        diakEredFelvisz(diakId,kerdesek.get(0).getFoKategoria(),kerdesek.get(0).getAlkategoria(),kerdesSorrend,pontSzamSorrend,diakValaszai);

        JOptionPane.showMessageDialog(null,"<html>A tesztnek vége, az ön eredménye:<br> Elérető maximális pontszám: "+maxPontszam+"" +
                "<br>Elért pontszám: "+elertpontszam+ "</html>");
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

    public boolean aktiveE(String fnev){
        boolean aktiv = false;
        String SQL_JELSZO = "SELECT aktiv FROM felhasznalok WHERE felhasznaloNev='"+fnev+"';";
        try {
            Statement stFelhasznalok = modell.getCON().createStatement();
            ResultSet rs = stFelhasznalok.executeQuery(SQL_JELSZO);
            if (rs.next() && rs.getInt(1)==0){
                aktiv = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aktiv;
    }

    /**
     * A bejelentkezés folyamata. Itt jön létre a Felhasználó objektum.
     * @param fnev
     */
    public void bejelentkezesFolyamat(String fnev) throws SQLException {
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
                view.getJt().addTab("Quiz",new DiakView(this,felhasznaloId));
                view.getContentPane().setBackground(new Color(164, 197, 249));
                view.setTitle("DiakQuiz");
                break;
            case 1:
                view.getJt().addTab("Quiz",new TanarView(this));
                view.getContentPane().setBackground(new Color(249, 244, 164));
                view.setTitle("TanarQuiz");
                break;
            case 2:
                view.getJt().addTab("Quiz",new AdminView(this));
                view.getContentPane().setBackground(new Color(249, 164, 164));
                view.setTitle("AdminQuiz");
                break;
                default:
                    System.out.println("Nincs ilyen típus!");
                    break;
        }
        view.getJt().addTab("Szótár",view.szotarFul());
        felhasznaloPanelBetolt(view.getJt());
        naplozas(felhasznaloId);
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
     * Ez a metódus létrehozza a diák felhasználókat az adatbázisban
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
     * Ez a metódus létrehozza a felhasználókat az adatbázisban
     * @param felhasznalonev
     * @param teljesNev
     * @param jelszo
     */
    public void regisztracio(String felhasznalonev, String teljesNev, String jelszo,int tipus,int aktiv){
        String SQL_UJFELHASZNALOT_LETREHOZZ = "INSERT INTO `felhasznalok` (`id`, `felhasznaloNev`, `teljesNev`, `jelszo`, `szint`, `tipus`, `joValaszDb`, `rosszValaszDb`,`aktiv`) " +
                "VALUES ( NULL , '" + felhasznalonev + "', '" + teljesNev + "','" + jelszo + "', '1', '"+tipus+"', '0', '0','"+aktiv+"')";
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
     * Felhasználót ad vissza.
     * @return
     */
    public Felhasznalo felhasznaloGetter(){
        return modell.getFelhasznalo();
    }

    public void ujablakmegynit(JFrame fr){
        fr.setVisible(true);
    }

    public ArrayList<Felhasznalo> felhasznalokLista(){
        ArrayList<Felhasznalo> felhasznL = new ArrayList<>();

        String SQL_FELHASZNALOK = "SELECT * FROM felhasznalok";

        try {
            Statement st = modell.getCON().createStatement();
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
                "VALUES (NULL, '"+foKategoria+"', '"+alKategoria+"', '"+tipus+"', '"+kerdesSzovege+"', '"+valasz+"', '"+valaszlehetosegek+"', '"+pontszam+"')";
        try {
            Statement stm = modell.getCON().createStatement();
            stm.executeUpdate(SQL_KERDESLETREHOZ);
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
            Statement stm = modell.getCON().createStatement();
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
            stm = modell.getCON().createStatement();
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
        String SQL_KATBESZUR = "INSERT into fokategoriamagyarazat VALUES('"+katnev+"','"+katleiras+"')";

        try {
            Statement st = modell.getCON().createStatement();
            st.executeUpdate(SQL_KATBESZUR);
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
        String SQL_ALKATBESZUR = "INSERT INTO `alkategoriamagyarazat` (`katAzon`, `fokatid`, `katLeiras`, `pelda`) VALUES ('"+katnev+"', '"+fokat+"', '"+katleiras+"', NULL)";

        try {
            Statement st = modell.getCON().createStatement();
            st.executeUpdate(SQL_ALKATBESZUR);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Az adott főkategóriához tartozó alkategóriákat adja vissze.
     * @param fokat
     * @return
     */
    public ArrayList<String> fokatAlkategoriai(String fokat){
        ArrayList<String> list = new ArrayList<>();
        String SQL_KATAZON = "SELECT katAzon FROM alkategoriamagyarazat WHERE fokatid='"+fokat+"'";
        try{
            Statement st = modell.getCON().createStatement();
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
            Statement st = modell.getCON().createStatement();
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
     * Visszaadja a diákokat egy Felhasznalo típusú listában.
     * @return
     */
    public ArrayList<Felhasznalo> osszesDiak(){
        ArrayList<Felhasznalo> list = new ArrayList<>();
        String SQL_DIAKERED = "SELECT * FROM felhasznalok WHERE tipus=0";
        try{
            Statement st = modell.getCON().createStatement();
            ResultSet res = st.executeQuery(SQL_DIAKERED);
            while(res.next()){
                list.add(new Felhasznalo(res.getInt(1),res.getString(2),res.getString(3),res.getString(4),res.getInt(5),res.getInt(6),res.getInt(7),res.getInt(8),res.getInt(9)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void ujPaneletolt(JPanel jpRegi,JPanel jpUj){
        jpRegi.removeAll();
        jpRegi.add(jpUj);
        jpRegi.revalidate();
    }

    /**
     * Frissíti az adatbázisban lévő felhasználói adatokat.
     * @param aktiv
     * @param tipus
     * @param id
     */
    public void felhasznaloFrissit(int aktiv,int tipus,int id) throws SQLException {

            String SQL_FELHASZNFRISSIT = "UPDATE felhasznalok SET aktiv='" + aktiv + "', tipus='" + tipus + "' WHERE id='" + id + "'";
            try {
                Statement st = modell.getCON().createStatement();
                st.executeUpdate(SQL_FELHASZNFRISSIT);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public ArrayList<Kerdes> alkatKerdesei(String alkat){
        ArrayList<Kerdes> list = new ArrayList<>();
        String SQL_ALKATKERDESEI = "SELECT * FROM kerdes WHERE alKategoria='"+alkat+"'";
        try {
            Statement st = modell.getCON().createStatement();
            ResultSet res = st.executeQuery(SQL_ALKATKERDESEI);
            while (res.next()){
                list.add(new Kerdes(res.getInt(1),res.getString(2),res.getString(3),res.getInt(4),res.getString(5),res.getString(6),res.getString(7),res.getInt(8)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }

    public KeyListener gepelFiegyel(JTextField jtf, int index, String[] list){


    KeyListener klGepel = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            list[index]=jtf.getText();
            }
    };
    return klGepel;
    }

    public ActionListener kepnevTombhoz(JRadioButton jrb, String[] tomb, int index, String filenev){
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tomb[index]=filenev;
            }
        };
        return al;
    }



    private void diakEredFelvisz(int diakid,String fokat,String alkat,String kerdesSorrend,String elertPontszamSorrend,String diakValaszai){
//        String SQL_DIAKEREDINSERT = "INSERT into 'diakered' VALUES('"+diakid+"','"+fokat+"','"+alkat+"','"+kerdesSorrend+"','"+elertPontszamSorrend+"','"+diakValaszai+"',NULL')";
        String SQL_DIAKEREDINSERT = "INSERT INTO `diakered` (`diakid`, `fokatid`, `alkatid`, `kerdesidsorrend`, `elertpontszamsorrend`, `diakvalaszai`, `beadasidopont`)" +
                " VALUES ('"+diakid+"','"+fokat+"','"+alkat+"','"+kerdesSorrend+"','"+elertPontszamSorrend+"','"+diakValaszai+"', CURRENT_TIMESTAMP)";

        try {
            Statement st = modell.getCON().createStatement();
            st.executeUpdate(SQL_DIAKEREDINSERT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ActionListener fileChooserMegynit(JFileChooser jfc){
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jfc.showDialog(null,"Válasszon egy filet");

            }
        };
        return al;
    }

    public ActionListener fileNevBeir(JFileChooser jfc,JTextField jtf){
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    jtf.setText(jfc.getSelectedFile().getName());
            }
        };
        return al;
    }

    public PopupMenuListener fajlNevek(JTextField jtf,JTextField jtf2, JTextField jtf3,JTextField jtf4,JComboBox jcbValasz){
        PopupMenuListener ppls = new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                jcbValasz.removeAllItems();
                if (jtf.getText().length()!=0){
                    jcbValasz.addItem(jtf.getText());
                }
                if (jtf2.getText().length()!=0){
                    jcbValasz.addItem(jtf2.getText());
                }
                if (jtf3.getText().length()!=0){
                    jcbValasz.addItem(jtf3.getText());
                }
                if (jtf4.getText().length()!=0){
                    jcbValasz.addItem(jtf4.getText());
                }
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        };
        return ppls;
    }


    public void fileMasol(File forras) {

        File cel = new File("./Images/"+forras.getName());
        System.out.println("cél: "+Paths.get("/Images"));
        System.out.println("forrás: "+forras.getAbsolutePath());
      try {
         Files.copy(forras.toPath(),cel.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void naplozas(int id) throws SQLException {
        String SQL_NAPLO = "INSERT INTO `naplo` (`bejelentkezesdatum`, `felhasznid`) VALUES (CURRENT_TIMESTAMP, '"+id+"')";

        Statement st = modell.getCON().createStatement();
        st.executeUpdate(SQL_NAPLO);
    }


    public void kerdesSzerk(String kerdesSzov,String valasz,String valaszLehet,int pontszam,int id) throws SQLException {
        String SQL_KERDESUPDATE = "UPDATE `kerdes` SET kerdesSzovege = ? , valasz= ? , valaszlehetosegek=? , pontszam = ? WHERE `kerdes`.`id`  = ?;";
        PreparedStatement ps = modell.getCON().prepareStatement(SQL_KERDESUPDATE);
        ps.setString(1, kerdesSzov);
        ps.setString(2, valasz);
        ps.setString(3, valaszLehet);
        ps.setInt(4, pontszam);
        ps.setInt(5, id);
        ps .executeUpdate();
    }

    public Modell getModell() {
        return modell;
    }

    public ArrayList<ArrayList<String>> diakEredmenyLista() throws SQLException {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        String SQL_DIAKERED = "SELECT * FROM diakered";

        Statement st = modell.getCON().createStatement();
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

        Statement st = modell.getCON().createStatement();
        st.executeUpdate(SQL_KERDESTOROL);
    }

    public String diakNev(int diakId) throws SQLException {
        String diaknev = "";
        String SQL_DIAKIDTODIAKNEV = "SELECT teljesnev FROM felhasznalok WHERE id='"+diakId+"'";
        Statement st = modell.getCON().createStatement();

        ResultSet res = st.executeQuery(SQL_DIAKIDTODIAKNEV);
        while(res.next()){
            diaknev=res.getString(1);
        }
        return diaknev;
    }



}
