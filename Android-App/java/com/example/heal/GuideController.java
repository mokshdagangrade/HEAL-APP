package com.example.heal;

public class GuideController {
    public static byte index, index1 = -1;

    /** medicinesNames 2D array */
    static int[][] medicinesNames = {
            {R.string.heartStep1, R.string.heartStep2, R.string.heartStep3, R.string.heartStep4, R.string.heartStep5, R.string.heartStep6},
            {R.string.fracStep1, R.string.fracStep2, R.string.fracStep3, R.string.fracStep4, R.string.fracStep5, R.string.fracStep6, R.string.fracStep7, R.string.fracStep8},
            {R.string.burnStep1, R.string.burnStep2, R.string.burnStep3, R.string.burnStep4},
            {R.string.cutStep1, R.string.cutStep2, R.string.cutStep3, R.string.cutStep4, R.string.cutStep5, R.string.cutStep6, R.string.cutStep7},
            {R.string.heatStep1, R.string.heatStep2, R.string.heatStep3},
            {R.string.elecStep1, R.string.elecStep2, R.string.elecStep3, R.string.elecStep4, R.string.elecStep5, R.string.elecStep6, R.string.elecStep7},
            {R.string.biteStep1, R.string.biteStep2, R.string.biteStep3, R.string.biteStep4, R.string.biteStep5, R.string.biteStep6, R.string.biteStep7, R.string.biteStep8},
            {R.string.chokeStep1, R.string.chokeStep2},
            {R.string.seizStep1, R.string.seizStep2, R.string.seizStep3, R.string.seizStep4},
            {R.string.eyeStep1, R.string.eyeStep2, R.string.eyeStep3, R.string.eyeStep4, R.string.eyeStep5, R.string.eyeStep6}
    };

    /** medicine descriptions 2D array */
    static int[][] descriptions = {
            {R.string.heartStep1desc, R.string.heartStep2desc, R.string.heartStep3desc, R.string.heartStep4desc, R.string.heartStep5desc, R.string.heartStep6desc},
            {R.string.fracStep1desc, R.string.fracStep2desc, R.string.fracStep3desc, R.string.fracStep4desc, R.string.fracStep5desc, R.string.fracStep6desc, R.string.fracStep7desc, R.string.fracStep8desc},
            {R.string.burnStep1desc, R.string.burnStep2desc, R.string.burnStep3desc, R.string.burnStep4desc},
            {R.string.cutStep1desc, R.string.cutStep2desc, R.string.cutStep3desc, R.string.cutStep4desc, R.string.cutStep5desc, R.string.cutStep6desc, R.string.cutStep7desc},
            {R.string.heatStep1desc, R.string.heatStep2desc, R.string.heatStep3desc},
            {R.string.elecStep1desc, R.string.elecStep2desc, R.string.elecStep3desc, R.string.elecStep4desc, R.string.elecStep5desc, R.string.elecStep6desc, R.string.elecStep7desc},
            {R.string.biteStep1desc, R.string.biteStep2desc, R.string.biteStep3desc, R.string.biteStep4desc, R.string.biteStep5desc, R.string.biteStep6desc, R.string.biteStep7desc, R.string.biteStep8desc},
            {R.string.chokeStep1desc, R.string.chokeStep2desc},
            {R.string.seizStep1desc, R.string.seizStep2desc, R.string.seizStep3desc, R.string.seizStep4desc},
            {R.string.eyeStep1desc, R.string.eyeStep2desc, R.string.eyeStep3desc, R.string.eyeStep4desc, R.string.eyeStep5desc, R.string.eyeStep6desc}
    };

    /** medicines imagesId 2D array  */
    static int[][] imagesId = {
            {R.drawable.heart1, R.drawable.heart2, R.drawable.heart3, R.drawable.heart4, R.drawable.heart5, R.drawable.heart6},
            {R.drawable.frac1, R.drawable.frac2, R.drawable.frac3, R.drawable.frac4, R.drawable.frac5, R.drawable.frac6, R.drawable.frac7, R.drawable.frac8},
            {R.drawable.burn1, R.drawable.burn2, R.drawable.burn3, R.drawable.burn4},
            {R.drawable.cuts1, R.drawable.cuts2, R.drawable.cuts3, R.drawable.cuts4, R.drawable.cuts5, R.drawable.cuts6, R.drawable.cuts7},
            {R.drawable.heat01, R.drawable.heat02, R.drawable.heat03},
            {R.drawable.elec1, R.drawable.elec2, R.drawable.elec3, R.drawable.elec4, R.drawable.elec5, R.drawable.elec6, R.drawable.elec7},
            {R.drawable.poison1, R.drawable.poison2, R.drawable.poison3, R.drawable.poison4, R.drawable.poison5, R.drawable.poison6, R.drawable.poison7, R.drawable.poison8},
            {R.drawable.choking1, R.drawable.choking2},
            {R.drawable.seizure1, R.drawable.seizure2, R.drawable.seizure3, R.drawable.seizure4},
            {R.drawable.eye1, R.drawable.eye2, R.drawable.eye3, R.drawable.eye4, R.drawable.eye5, R.drawable.eye6}
    };

    /**
     *  getMedicine name method
     *  @return medicinesNames
     * */
    public static int[] getMedicines(){
        return medicinesNames[index];
    }

    /**
     * getDescriptions method
     * @return descriptions
     * */
    public static int[] getDescriptions(){
        return descriptions[index];
    }

    /**
     * getImagesId method
     * @return imagesId
     * */
    public static int[] getImagesId(){
        return imagesId[index];
    }
}

