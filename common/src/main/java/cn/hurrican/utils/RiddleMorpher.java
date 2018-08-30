package cn.hurrican.utils;

import cn.hurrican.model.Riddle;
import net.sf.ezmorph.ObjectMorpher;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/8/30
 * @Modified 10:47
 */

public class RiddleMorpher implements ObjectMorpher {
    /**
     * Morphs the input object into an output object of the supported type.
     *
     * @param value The input value to be morphed
     * @throws MorphException if conversion cannot be performed successfully
     */
    @Override
    public Object morph(Object value) {
        if(value == null){
            return null;
        }
        System.out.println(value);
        return null;
    }

    /**
     * Returns the target Class for conversion.
     *
     * @return the target Class for conversion.
     */
    @Override
    public Class morphsTo() {
        return Riddle.class;
    }

    /**
     * Returns true if the Morpher supports conversion from this Class.
     *
     * @param clazz the source Class
     * @return true if clazz is supported by this morpher, false otherwise.
     */
    @Override
    public boolean supports(Class clazz) {
        if(clazz.equals(Riddle.class)){
            return true;
        }
        return false;
    }
}
