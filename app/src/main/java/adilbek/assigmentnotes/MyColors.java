package adilbek.assigmentnotes;

/**
 * Created by adilbek on 22.10.17.
 */

public class MyColors {
    int colorsList[] = new int[] {R.color.changeColorOneYellow, R.color.changeColorTwoLightBlue, R.color.changeColorThreeDarkBlue,
            R.color.changeColorFourRed};
    private int counter = 0;

    public MyColors(int thisColor) {
        for(int index=0; index<colorsList.length; index++)
            if(thisColor==colorsList[index]){
                counter = index;
                break;
            }
    }

    public MyColors(){}

    public int getNextColor() {
        counter = addCounter(counter);
        return colorsList[counter];
    }

    public int getColor() {
        return colorsList[counter];
    }

    private int addCounter(int counter) {
        counter += 1;
        if(counter >= colorsList.length)
            counter = 0;
        return counter;
    }
}
