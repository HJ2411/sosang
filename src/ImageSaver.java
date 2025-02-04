import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;


import javax.swing.*;

import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageSaver extends JFrame  {
    private Container cp;

    ImageSaver(){
        cp = getContentPane();
    }

    public String getExtension(File fi) {
        String fileName = fi.getName();
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if (lastIndexOfDot == -1) {
            return ""; // 확장자가 없는 경우 빈 문자열 반환
        }
        return fileName.substring(lastIndexOfDot);
    }
    public String getExtension(String fi){
        int lastIndexOfDot = fi.lastIndexOf(".");
        if (lastIndexOfDot == -1) {
            return "";
        }
        return fi.substring(lastIndexOfDot);
    }
    boolean saveUnplus(String name,String target) {
        char[] cs = new char[512];
        JFileChooser jFile = new JFileChooser();

        jFile.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "tif"));

        int fCheck = jFile.showSaveDialog(null);
        if(fCheck==JFileChooser.APPROVE_OPTION) {
            if(!new File("./image").exists())
                makedir();

            FileInputStream fis = null;
            FileOutputStream fos = null;
            File fi = new File(jFile.getSelectedFile().getPath());



            File fo = new File("./image/"+name);
            try {
                fis = new FileInputStream(fi.getPath());
                fos = new FileOutputStream("./image/"+name+getExtension(fi));
                int i=0;
                byte[] bs = fis.readAllBytes();
                if(fo.exists())
                    fo.delete();

                fos.write(bs);
                fos.flush();

                return true;
            }catch(FileNotFoundException fe) {}
            catch(IOException ie) {}
            finally {
                try {
                    fos.close();
                    fis.close();
                }catch (IOException e) {}
            }
        }
        return false;
    }
    //파라미터 변수는 파일의 이름이됨
    String save(String name,String target) {
        char[] cs = new char[512];
        JFileChooser jFile = new JFileChooser();
        int num = LastPlus(target);
        String result = "";
        jFile.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "tif"));

        int fCheck = jFile.showSaveDialog(null);
        if(fCheck==JFileChooser.APPROVE_OPTION) {
            if(!new File("./image").exists())
                makedir();

            FileInputStream fis = null;
            FileOutputStream fos = null;
            File fi = new File(jFile.getSelectedFile().getPath());
            File fo = new File("./image/"+name);
            
            result = num+getExtension(fi);
            try {
                fis = new FileInputStream(fi.getPath());
                fos = new FileOutputStream("./image/"+name+num+getExtension(fi));
                int i=0;
                byte[] bs = fis.readAllBytes();
                if(fo.exists())
                    fo.delete();

                fos.write(bs);
                fos.flush();


            }catch(FileNotFoundException fe) {}
            catch(IOException ie) {}
            finally {
                try {
                    fos.close();
                    fis.close();
                }catch (IOException e) {}
            }
        }
        return result;
    }
    boolean save(String name, JLabel fileLabel,String target) {
        char[] cs = new char[512];
        JFileChooser jFile = new JFileChooser();

        jFile.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "tif"));

        int fCheck = jFile.showSaveDialog(null);
        if(fCheck==JFileChooser.APPROVE_OPTION) {
            if(!new File("./image").exists())
                makedir();

            FileInputStream fis = null;
            FileOutputStream fos = null;
            File fi = new File(jFile.getSelectedFile().getPath());



            File fo = new File("./image/"+name);
            try {
                fis = new FileInputStream(fi.getPath());
                fos = new FileOutputStream("./image/"+name+LastPlus(target)+getExtension(fi));
                int i=0;
                byte[] bs = fis.readAllBytes();
                if(fo.exists())
                    fo.delete();

                fos.write(bs);
                fos.flush();
                fileLabel.setText(jFile.getSelectedFile().getName());
                return true;
            }catch(FileNotFoundException fe) {}
            catch(IOException ie) {}
            finally {
                try {
                    fos.close();
                    fis.close();
                }catch (IOException e) {}
            }
        }
        return false;
    }

    int LastPlus(String target){
        String Query = "select * from "+target;

        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        int result = 0;
        try{
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(Query);
            rs.last();
            result = rs.getRow()+1;
        } catch (SQLException e) {
            System.out.println("드라이버 연결 실패");
        }finally {
            try{
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    void makedir() {
        String line="./image";
        File fp = new File(line);
        System.out.println(fp.exists());
        if(!fp.exists())
            fp.mkdirs();

    }

    public String findImageWithExtension(String baseName) {
        String[] extensions = {".jpg", ".png", ".jpeg", ".gif", ".tif"};
        for (String ext : extensions) {
            File file = new File(baseName + ext);
            if (file.exists()) {
                return baseName + ext;
            }
        }
        return baseName; // 기본적으로 확장자가 없는 경우 반환
    }

    public ImageIcon loadImage(String filePath) {
        ImageIcon imageIcon = new ImageIcon(filePath);
        Image image = imageIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

}