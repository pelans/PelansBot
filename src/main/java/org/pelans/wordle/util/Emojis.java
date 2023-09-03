package org.pelans.wordle.util;

import java.util.HashMap;
import java.util.Map;

public class Emojis {
    private static Map<Character,String> greenEmoji;
    private static Map<Character,String> yellowEmoji;
    private static Map<Character,String> blackEmoji;
    private static Map<Character,String> greyEmoji;
    public static void init() {
        greenEmoji = new HashMap<Character, String >();
        greenEmoji.put('a',"<:green_A:1147856304883388506>");
        greenEmoji.put('á',"<:green_Aa:1147856306271703100>");
        greenEmoji.put('b',"<:green_B:1147856308578558094>");
        greenEmoji.put('c',"<:green_C:1147856310503747624>");
        greenEmoji.put('d',"<:green_D:1147856312919662593>");
        greenEmoji.put('e',"<:green_E:1147856314479935568>");
        greenEmoji.put('é',"<:green_Ee:1147856316975546429>");
        greenEmoji.put('f',"<:green_F:1147856319416643676>");
        greenEmoji.put('g',"<:green_G:1147856344322408448>");
        greenEmoji.put('h',"<:green_H:1147856345974968381>");
        greenEmoji.put('i',"<:green_I:1147856347602354237>");
        greenEmoji.put('í',"<:green_Ii:1147856349045215303>");
        greenEmoji.put('j',"<:green_J:1147856351595331634>");
        greenEmoji.put('k',"<:green_K:1147856352903970939>");
        greenEmoji.put('l',"<:green_L:1147856355420549252>");
        greenEmoji.put('m',"<:green_M:1147856356997611571>");
        greenEmoji.put('n',"<:green_N:1147856387389534289>");
        greenEmoji.put('ñ',"<:green_Nn:1147856389570568193>");
        greenEmoji.put('o',"<:green_O:1147856391218942022>");
        greenEmoji.put('ó',"<:green_Oo:1147856392166854759>");
        greenEmoji.put('p',"<:green_P:1147856394788294697>");
        greenEmoji.put('q',"<:green_Q:1147856396247900240>");
        greenEmoji.put('r',"<:green_R:1147856398487662623>");
        greenEmoji.put('s',"<:green_S:1147856400115056690>");
        greenEmoji.put('t',"<:green_T:1147856428254638213>");
        greenEmoji.put('u',"<:green_U:1147856430892847154>");
        greenEmoji.put('ú',"<:green_Uu:1147856432071446589>");
        greenEmoji.put('ü',"<:green_uUu:1147856433786929172>");
        greenEmoji.put('v',"<:green_V:1147856442901147769>");
        greenEmoji.put('w',"<:green_W:1147856444088135780>");
        greenEmoji.put('x',"<:green_X:1147856445988147222>");
        greenEmoji.put('y',"<:green_Y:1147856447418404956>");
        greenEmoji.put('z',"<:green_Z:1147856449406500914>");

        yellowEmoji = new HashMap<Character, String >();
        yellowEmoji.put('a',"<:yellow_A:1147856720366931973>");
        yellowEmoji.put('á',"<:yellow_Aa:1147856723093246083>");
        yellowEmoji.put('b',"<:yellow_B:1147856724288602152>");
        yellowEmoji.put('c',"<:yellow_C:1147856725463007272>");
        yellowEmoji.put('d',"<:yellow_D:1147856727182676062>");
        yellowEmoji.put('e',"<:yellow_E:1147856728940097657>");
        yellowEmoji.put('é',"<:yellow_Ee:1147856730504577086>");
        yellowEmoji.put('f',"<:yellow_F:1147856733511884900>");
        yellowEmoji.put('g',"<:yellow_G:1147856783306657803>");
        yellowEmoji.put('h',"<:yellow_H:1147856785345101874>");
        yellowEmoji.put('i',"<:yellow_I:1147856786758586459>");
        yellowEmoji.put('í',"<:yellow_Ii:1147856788352413696>");
        yellowEmoji.put('j',"<:yellow_Ii:1147856788352413696>");
        yellowEmoji.put('k',"<:yellow_K:1147857284375003198>");
        yellowEmoji.put('l',"<:yellow_L:1147857286795104386>");
        yellowEmoji.put('m',"<:yellow_M:1147857289831796747>");
        yellowEmoji.put('n',"<:yellow_N:1147857291970871320>");
        yellowEmoji.put('ñ',"<:yellow_Nn:1147857294437126245>");
        yellowEmoji.put('o',"<:yellow_O:1147857297012428865>");
        yellowEmoji.put('ó',"<:yellow_Oo:1147857299533213726>");
        yellowEmoji.put('p',"<:yellow_P:1147857300896354414>");
        yellowEmoji.put('q',"<:yellow_Q:1147857303253549066>");
        yellowEmoji.put('r',"<:yellow_R:1147857304490876928>");
        yellowEmoji.put('s',"<:yellow_S:1147857335931392060>");
        yellowEmoji.put('t',"<:yellow_T:1147857338317938730>");
        yellowEmoji.put('u',"<:yellow_U:1147857340293468200>");
        yellowEmoji.put('ú',"<:yellow_Uu:1147857342466113638>");
        yellowEmoji.put('ü',"<:yellow_uUu:1147857343648899174>");
        yellowEmoji.put('v',"<:yellow_V:1147857345490194503>");
        yellowEmoji.put('w',"<:yellow_W:1147857348044525569>");
        yellowEmoji.put('x',"<:yellow_X:1147857349298638928>");
        yellowEmoji.put('y',"<:yellow_Y:1147857351458705509>");
        yellowEmoji.put('z',"<:yellow_Z:1147857353094475858>");

        blackEmoji = new HashMap<Character, String >();
        blackEmoji.put('a',"<:black_A:1147854658782642276>");
        blackEmoji.put('á',"<:black_Aa:1147854659957031015>");
        blackEmoji.put('b',"<:black_B:1147854662930804777>");
        blackEmoji.put('c',"<:black_C:1147854664667238412>");
        blackEmoji.put('d',"<:black_D:1147854666101694524>");
        blackEmoji.put('e',"<:black_E:1147854667313848330>");
        blackEmoji.put('é',"<:black_Ee:1147854669658460220>");
        blackEmoji.put('f',"<:black_F:1147854670929338369>");
        blackEmoji.put('g',"<:black_G:1147854672774836316>");
        blackEmoji.put('h',"<:black_H:1147854675366920232>");
        blackEmoji.put('i',"<:black_I:1147854676721680474>");
        blackEmoji.put('í',"<:black_Ii:1147854757814337636>");
        blackEmoji.put('j',"<:black_J:1147854779448557658>");
        blackEmoji.put('k',"<:black_K:1147854791020662854>");
        blackEmoji.put('l',"<:black_L:1147854804077514832>");
        blackEmoji.put('m',"<:black_M:1147854863707938876>");
        blackEmoji.put('n',"<:black_N:1147854865897373716>");
        blackEmoji.put('ñ',"<:black_Nn:1147854867289878638>");
        blackEmoji.put('o',"<:black_O:1147854868166479903>");
        blackEmoji.put('ó',"<:black_Oo:1147854871047970906>");
        blackEmoji.put('p',"<:black_P:1147868274835603486>");
        blackEmoji.put('q',"<:black_Q:1147854872608260158>");
        blackEmoji.put('r',"<:black_R:1147854875598790736>");
        blackEmoji.put('s',"<:black_S:1147854876836106320>");
        blackEmoji.put('t',"<:black_T:1147854907173503006>");
        blackEmoji.put('u',"<:black_U:1147854909480370227>");
        blackEmoji.put('ú',"<:black_Uu:1147854911447519282>");
        blackEmoji.put('ü',"<:black_uUu:1147854914337386658>");
        blackEmoji.put('v',"<:black_V:1147854915755053126>");
        blackEmoji.put('w',"<:black_W:1147854917780918343>");
        blackEmoji.put('x',"<:black_X:1147854919316017232>");
        blackEmoji.put('y',"<:black_Y:1147855484272001074>");
        blackEmoji.put('z',"<:black_Z:1147855490978693190>");

        greyEmoji = new HashMap<Character, String >();
        greyEmoji.put('a',"<:grey_A:1147855898245599252>");
        greyEmoji.put('á',"<:grey_Aa:1147855900992872528>");
        greyEmoji.put('b',"<:grey_B:1147855903001935883>");
        greyEmoji.put('c',"<:grey_C:1147855905027805224>");
        greyEmoji.put('d',"<:grey_D:1147855906424500254>");
        greyEmoji.put('e',"<:grey_E:1147855907938652290>");
        greyEmoji.put('é',"<:grey_Ee:1147855909784125510>");
        greyEmoji.put('f',"<:grey_F:1147855911080165396>");
        greyEmoji.put('g',"<:grey_G:1147855950611501208>");
        greyEmoji.put('h',"<:grey_H:1147855952792522833>");
        greyEmoji.put('i',"<:grey_I:1147855954579308595>");
        greyEmoji.put('í',"<:grey_Ii:1147855956965859328>");
        greyEmoji.put('j',"<:grey_J:1147855957838286951>");
        greyEmoji.put('k',"<:grey_K:1147855959557935225>");
        greyEmoji.put('l',"<:grey_L:1147855961978056705>");
        greyEmoji.put('m',"<:grey_M:1147855963613835294>");
        greyEmoji.put('n',"<:grey_N:1147856007125532712>");
        greyEmoji.put('ñ',"<:grey_Nn:1147856008362868847>");
        greyEmoji.put('o',"<:grey_O:1147856010451636244>");
        greyEmoji.put('ó',"<:grey_Oo:1147856011730886766>");
        greyEmoji.put('p',"<:grey_P:1147856019452596264>");
        greyEmoji.put('q',"<:grey_Q:1147856022086631444>");
        greyEmoji.put('r',"<:grey_R:1147856024955531285>");
        greyEmoji.put('s',"<:grey_S:1147856026691977246>");
        greyEmoji.put('t',"<:grey_T:1147856065539616850>");
        greyEmoji.put('u',"<:grey_U:1147856067942940692>");
        greyEmoji.put('ú',"<:grey_Uu:1147856069339648160>");
        greyEmoji.put('ü',"<:grey_uUu:1147856070656663582>");
        greyEmoji.put('v',"<:grey_V:1147856072250503190>");
        greyEmoji.put('w',"<:grey_W:1147856073802403983>");
        greyEmoji.put('x',"<:grey_X:1147856075287171134>");
        greyEmoji.put('y',"<:grey_Y:1147856077661143090>");
        greyEmoji.put('z',"<:grey_Z:1147856079603122198>");
    }

    public static String getGreen(char letter) {
        if (!greenEmoji.containsKey(letter))
            return String.valueOf(letter);
        return greenEmoji.get(letter);
    }

    public static String getYellow(char letter) {
        if (!yellowEmoji.containsKey(letter))
            return String.valueOf(letter);
        return yellowEmoji.get(letter);
    }

    public static String getBlack(char letter) {
        if (!blackEmoji.containsKey(letter))
            return String.valueOf(letter);
        return blackEmoji.get(letter);
    }

    public static String getGrey(char letter) {
        if (!greyEmoji.containsKey(letter))
            return String.valueOf(letter);
        return greyEmoji.get(letter);
    }

}
