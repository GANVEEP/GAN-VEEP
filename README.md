# GAN-VEEP
This framework can be used to predict the future trajectory of vehicles in the urban road scene.
This framework is mainly divided into two parts, the Gan model written by Python and the experimental platform built by Java.
The code of the Gan model is in the Gan directory. In the checkpoint folder are the prediction models we have trained. Finalmodel.py is the code of Gan model, which is used to train prediction model.
The code of the experimental platform built by Java is in the SRC directory, including all the codes of preprocessing data, generating training data, and calling the Gan model for vehicle trajectory prediction.
The process of training data generation is described here:
1. Add the TCL file of vehicle track generated by SUMO into getlaneinformatio.java, run the main function to generate the historical information of each vehicle.
2. Run the main function in generationtraindata.java to store all vehicle information in the traindata folder.
3. execute the main function in changenext.java, which is used to convert the cross road vehicle information generated in the previous step to coordinate and store it in the okvehicle.txt file.
The process of generating forecast data is described here:
1. Run the main function in the mainapp file, and the vehicle track at a specific time will be extracted in line 23. This code will predict the vehicle track data at the next time at that time.
2. The code will iteratively generate the vehicle trajectory in the future time.
Describe the process of evaluating forecast data here, remember
1. In everyroad.java, it is to evaluate the number of vehicles on each road and the average speed of vehicles on each road.
2. Getevaluaionmetrics.java generates evaluation data in the console.
4. Getall.java is used to process the tracks of all vehicles and get the average position and speed difference according to the tracks.
