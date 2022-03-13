/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import javafx.scene.input.KeyEvent;

/**
 *
 * @author angel
 */
public class Solonumeros {
     public void SoloNumerosEnteros(KeyEvent keyEvent) {
        try {
            char key = keyEvent.getCharacter().charAt(0);

            if (!Character.isDigit(key)) {
                keyEvent.consume();
            }

        } catch (Exception ex) {
        }
    }
     public void SoloLetras(KeyEvent keyEvent) {
    try{
        char key = keyEvent.getCharacter().charAt(0);
        if(!Character.isLetter(key))
            keyEvent.consume();

    }catch (Exception ex){  }
}
//     VARIABLETEXTFIELDtxtcpcliente.addEventHandler(KeyEvent.KEY_TYPED, event -> (OBJETO EN EL PROGRAMA)validarnumeros.SoloNumerosEnteros(event));
    //    VARIABLE TEXTFIELDtxtcelularcliente.addEventHandler(KeyEvent.KEY_TYPED, event -> (OBJETO EN EL PROGRAMA)validarnumeros.SoloNumerosEnteros(event));

    
}
