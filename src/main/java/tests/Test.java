package tests;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class Test {
    // Remplacez ces valeurs par vos propres identifiants Twilio
    public static final String ACCOUNT_SID = "AC01038dc9d5e05a8a7b4376d49195b3c1";
    public static final String AUTH_TOKEN = "d19d742787d2a53ef2c0f2ceebc7de19";

    public static void main(String[] args) {
        // Initialisez la bibliothèque Twilio avec vos identifiants
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Numéro de téléphone Twilio à partir duquel vous envoyez le SMS (il doit être vérifié dans Twilio)
        String twilioNumber = "+18154280055";

        // Numéro de téléphone du destinataire
        String recipientNumber = "+21651420180";

        // Contenu du SMS
        String messageBody = "Ceci est un exemple de SMS envoyé depuis Twilio.";

        // Envoi du SMS
        Message message = Message.creator(
                        new PhoneNumber(recipientNumber),
                        new PhoneNumber(twilioNumber),
                        messageBody)
                .create();

        // Affichage de l'identifiant du message si l'envoi réussit
        System.out.println("Message SID: " + message.getSid());
    }
}
