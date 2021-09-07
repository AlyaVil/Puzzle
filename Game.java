//как ячейкам отобржаться, перемещаться, перемешиватья
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Game extends JFrame {
    private ArrayList<Point> solution;
    private ArrayList<PuzzleButton> buttons;
    private JPanel panel;
    private BufferedImage source;
    private BufferedImage resized;
    private int width, height;
    private final int DESIRED_WIDTH =400;
    private Image image;
    private PuzzleButton LastButton; //пустая ячейка
    private PuzzleButton lastButton;

    public Game(){
        initUI();
    }
    public void initUI(){
        solution= new ArrayList<>();
        solution.add(new Point(0,0));
        solution.add(new Point(0,1));
        solution.add(new Point(0,2));
        solution.add(new Point(1,0));
        solution.add(new Point(1,1));
        solution.add(new Point(1,2));
        solution.add(new Point(2,0));
        solution.add(new Point(2,1));
        solution.add(new Point(2,2));
        solution.add(new Point(3,0));
        solution.add(new Point(3,1));
        solution.add(new Point(3,2));

       buttons=new ArrayList<>();

       panel= new JPanel();
       panel.setBorder(BorderFactory.createLineBorder(Color.gray)); //разделение ячеек серым
       panel.setLayout(new GridLayout(4, 3));  //кол-во строк и столбцов

        try {

        source = LoadImage();
        int h = getNewHeight(source.getWidth(), source.getHeight());
        resized= resizeImage(source, DESIRED_WIDTH, h, BufferedImage.TYPE_INT_ARGB);

    }catch (IOException ex) { //исклюение
            System.out.println("Problems with source image"+ex);
        }

        width = resized.getWidth();
        height = resized.getHeight();

        add(panel, BorderLayout.CENTER);

        for (int i = 0; i < 4; i++)  //4 строчки, высота,y
            for (int j=0; j<3; j++) {  //3 столбца, ширинабч
                image = createImage(new FilteredImageSource(resized.getSource(), new CropImageFilter(j*width/3, i*height/4, width/3, height/4 )));

                PuzzleButton button= new PuzzleButton(image);
                button.putClientProperty("position", new Point(i,j));

                if (i==3 && j==2){
                    lastButton = new PuzzleButton();
                    lastButton.setBorderPainted(false); //не отрисовываем рамку
                    lastButton.setContentFilled(false); //не рисуем кртинку внутри ячейки
                    lastButton.setLastButton(true);
                } else {
                    buttons.add(button);
                }
            }

        }
        private BufferedImage resizeImage(BufferedImage originImage, int width, int height, int type){  //рисуем картинку нужного размера
            BufferedImage resizeImage = new BufferedImage(width, height, type);
            Graphics2D g = resizeImage.createGraphics();
            g.drawImage(originImage,0 ,0, width, height, null);
            g.dispose();
            return resizeImage;

        }

        private BufferedImage LoadImage() throws IOException { //для загрузки изображения
          BufferedImage bimg = ImageIO.read(new File("sunset.jpg"));
          return bimg;
    }
        private int getNewHeight(int w, int h){          //возвращает новую высоту картинки
        double ratio = DESIRED_WIDTH/(double)w;        //соотношение реальной картинки к желаемой
        int newHeight= (int)(h*ratio);                  //домножаем высоту на соотношение
        return newHeight;

    }
}
