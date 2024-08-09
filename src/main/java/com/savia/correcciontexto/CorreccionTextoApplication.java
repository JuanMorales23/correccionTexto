package com.savia.correcciontexto;

import com.savia.correcciontexto.Service.CorrectorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CorreccionTextoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CorreccionTextoApplication.class, args);
        try {
            CorrectorService correctorService = new CorrectorService();

            String inputText = "El \n pacente cuen&a con 100kls de peso y 168 cent&metros de altura con 18 a&os";
            String correctedText = correctorService.correctText(inputText);
            System.out.println("Texto original: " + inputText);
            System.out.println("Texto corregido: " + correctedText);

            // String de 2mil Caracteres
            String inputText2 = "Paciente de 45 años, masculino, que acude a consulta con una queja principal de dolor en el estómago y dificultad para respirar. Refere que el dolor empezó hace 3 semanas y ha aumentado en intensidad. El paciente menciona haber tenido episodios de náuseas y vómitos, especialmente despues de las comidas. Se observan signos de hinchazón abdominal y la presión arterial está ligeramente elevada. Se le pregunta sobre sus antecedentes médicos y refiere que tiene historial de hipertensión y diabetes tipo 2, ambas condiciones mal controladas.\n" +
                    "\n" +
                    "En el examen físico, se encuentra que el paciente presenta una frecuencia cardíaca de 95 latidos por minuto y una temperatura corporal de 37.8°C. Se notan signos de taquicardia y se escuchan estertores en los campos pulmonares, lo que puede indicar una infección respiratoria. También se observa que el paciente ha perdido aproximadamente 5 kilogramos en las últimas 2 semanas, lo que es preocupante.\n" +
                    "\n" +
                    "El paciente informa que ha estado tomando medicamentos de venta libre para el dolor, pero sin mucho alivio. Además, refiere que tiene problemas para dormir debido al malestar persistente. Se le recomienda realizar pruebas adicionales, incluyendo análisis de sangre y radiografía de tórax, para evaluar la causa de sus síntomas. Se le aconseja también que mejore su dieta y que tome los medicamentos según las indicaciones.\n" +
                    "\n" +
                    "Se le recuerda que debe acudir a la consulta de seguimiento en 1 semana para revisar los resultados de las pruebas y ajustar el tratamiento según sea necesario. La importancia de mantener un seguimiento regular y ajustar el tratamiento es crucial para el manejo adecuado de sus condiciones crónicas. Se le proporciona educación sobre la gestión de la hipertensión y la diabetes, y se le ofrecen recursos adicionales para el apoyo en su tratamiento.\n" +
                    "\n" +
                    "Observaciones adicionales incluyen que el paciente tiene dificultades para comunicar los detalles de sus síntomas y a veces parece confuso sobre los nombres de los medicamentos que está tomando. Es importante realizar un seguimiento cercano y brindar apoyo adicional si es necesario.\n";

            //String de 5mil caracteres
            /*
            String inputText2 = "El pacnte llegó ala clinik kon uns síntomas muy preokupntes. Haba tenido dolor en el pzo por varis smanas y no había poddo domir bn. Al llegar, el pacnte se quejó de q tenia nauceas y kreo ke podra tener fiebre, aunke no traía termómetro konsigo. El doktor le hizó una evalúacion ráåpida y detecció ke el pacnte presentaba signos de fatiga severa, además de una preion arteial altísma.\n" +
                    "\n" +
                    "En su historia médca, el pacnte relto ke tomaba medcmentos para la diabetes, pero k aveces se olvidaba de tomrlos por la mñnana. Tambn mencionó ke había perdid algo de peso en los últimos meses, lo k le preocpaba pq no había hecho ningun cambio en su dieta. El doktr le recomendó hacer unos analisis de sangre para deteccion de posibles infeccciones o otrs problemaz q pudieran estar afectndole.\n" +
                    "\n" +
                    "Durant la evaluzion, el pacnt también comntó q a veses siente un dolor agudo en el pcho, especiálment cuando sub las escaeras. Esto preokupo ael doktor, q decidio hacer un elektrocardiograma para descartor cualquier problma k pudiera estar relacionad con el corazon. Aunke los resultados preliminares no mostraron anormalias grves, el doctor recomendo q el pacnte realisara un seguimiento continuo de su condicion y que evitara el ezfuerso fisico excesivo.\n" +
                    "\n" +
                    "Después de los exámenes, el pacnte fué derivado a un espcialista en cardiologia para una evaluaion más detallada. Entre tanto, se le indicaron medcamentos para controlr la presion arteial y se le aconsejo que cambiará algunos de sus habitos alimenticios, disminuyendo la ingesta de sal y grasa.\n" +
                    "\n" +
                    "En la consulta siguiente, el pacnte menciono que había experimentado menos episodios de dolor pectoral, pero que ahora tenía una tos persistente que le preocupaba. El doktor sugirio que podría ser una reaccion adversa a uno de los medcamentos preskribidos y decidió hacer ajustes en la medicación. Tambn le recomendó que incremtara su consumo de frutas y verduras para mejorar su salud en general.\n" +
                    "\n" +
                    "El pacnte fue insistente en que su malestar podría estar relaciondo con su trabajo, ya que pasa muchas horas del día sentado frente a una computadora. El doktor acordó que era importante considerar el estilo de vida del pacnte y le sugirio hacer pausas frecuentes, ejercitarse regularmente, y asegurarse de tener una postura adecuada mientras trabaja.\n" +
                    "\n" +
                    "A pesar de los tratamientos y consejos recibidos, el pacnte seguía experimentando fatiga y malestar general. En una consulta posterior, el doktor decidió remitir al pacnte a un endocrinólogo para descartar cualquier disfunción hormonal que pudiera estar contribuyendo a sus síntomas. Además, se le recomendó realizar una prueba de esfuerzo para evaluar su capacidad cardiovascular de manera más precisa.\n" +
                    "\n" +
                    "El pacnte también expresó su preocupación por un bulto que había notado en su cuello, que aunque no le causaba dolor, lo tenía bastante ansioso. El doktor le dijo que podría tratarse de una simple inflamación de un ganglio linfático, pero que sería necesario realizar una ecografía para obtener un diagnóstico más preciso.\n" +
                    "\n" +
                    "Durante esta misma consulta, el pacnte mencionó que había tenido episodios de mareo, especialmente cuando se levantaba rápidamente. El doctor consideró que estos mareos podrían estar relacionados con la presión arterial fluctuante, por lo que ajustó nuevamente la medicación y le sugirió que tomara medidas para evitar cambios bruscos de posición.\n" +
                    "\n" +
                    "El pacnte también relató que había estado experimentando problemas digestivos, como acidez estomacal frecuente y dificultad para digerir ciertos alimentos. El doktor le recomendó evitar comidas picantes y grasosas, y que consumiera más fibra para mejorar su digestión. Además, le prescribió un antiácido para aliviar los síntomas de la acidez.\n" +
                    "\n" +
                    "En las semanas siguientes, el pacnte reportó una ligera mejora en algunos de sus síntomas, pero aún se sentía abrumado por su estado de salud general. Decidió buscar una segunda opinión y acudió a otro especialista, quien le realizó una batería de pruebas adicionales. Estas pruebas revelaron que el pacnte tenía niveles elevados de azúcar en la sangre, lo que indicaba que su diabetes no estaba bien controlada.\n" +
                    "\n" +
                    "El nuevo doctor sugirió que el pacnte iniciara un programa de ejercicios supervisado y le remitió a un nutricionista para que recibiera orientación sobre una dieta adecuada para controlar su diabetes. Además, le recomendó realizar controles regulares de glucosa y mantener un registro detallado de sus niveles para ajustar el tratamiento según fuera necesario.\n" +
                    "\n" +
                    "A lo largo de varios meses, el pacnte trabajó de cerca con su equipo médico para mejorar su salud. Comenzó a hacer ejercicio regularmente, cambió su dieta y siguió todas las recomendaciones médicas. Poco a poco, empezó a notar mejoras significativas en su estado de ánimo y energía. Aunque todavía tenía días difíciles, el pacnte se sintió más optimista sobre su futuro y más capacitado para manejar su salud de manera proactiva.\n" +
                    "\n" +
                    "Sin embargo, a pesar de los progresos, el pacnte todavía enfrentaba desafíos en su vida diaria. Tenía que lidiar con el estrés del trabajo, los cambios en su rutina diaria, y el ajuste a un nuevo régimen de tratamiento. Afortunadamente, contaba con el apoyo de su familia y amigos, quienes le ayudaban a mantener una actitud positiva y le recordaban la importancia de cuidar su salud.\n" +
                    "\n" +
                    "En una de sus últimas consultas, el pacnte le agradeció a su doktor por toda la ayuda y orientación que había recibido a lo largo de su tratamiento. Aunque el camino había sido largo y difícil, el pacnte estaba agradecido por haber encontrado un equipo médico dedicado que le había ayudado a superar muchos de sus problemas de salud.\n" +
                    "\n" +
                    "Finalmente, el pacnte comprendió la importancia de tomar un enfoque integral hacia su bienestar. No sólo se trataba de seguir las recomendaciones médicas, sino también de hacer cambios en su estilo de vida que le permitieran vivir de manera más saludable y equilibrada. Con este nuevo entendimiento, el pacnte se comprometió a seguir cuidando de su salud y a continuar trabajando con su equipo médico para mantener su bienestar a largo plazo.";
            */
            String correctedText2 = correctorService.correctText(inputText2);
            System.out.println("Texto original: " + inputText2);
            System.out.println("Texto corregido: " + correctedText2);
        } catch (Exception e) {
            System.out.println("Error al corregir el texto: " + e.getMessage());
        }
    }

}
