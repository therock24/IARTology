/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handdetection;

import java.security.SecureRandom;
import java.util.Date;

/**
 *
 * @author AReis
 */

public class NeuralNetwork 
{
    // class variables so we can store the Layer Sizes and number of Hidden Layers
    private static int InputLayerSize;
    private static int HiddenLayerSize;
    private static int HiddenLayerNum;
    private static int OutputLayerSize;
    
    // Variables to store the Weights each node of the Network
    private static int ILWeights[][];
    private static int HLWeights[][][];
    private static int OLWeights[][];
    
    
    // Constructor method. We receive the sizes of all the Layers
    // and the amount of HiddenLayers passed, and save them.
    public void NeuralNetwork(int InputLayerSize,int HiddenLayerNum,int HiddenLayerSize,int OutputLayerSize)
    {
        // variables cicle control
        int i,j,k;
        
        // saving the passed parameters
        this.InputLayerSize = InputLayerSize;
        this.HiddenLayerSize = HiddenLayerSize;
        this.OutputLayerSize = HiddenLayerNum;
        this.HiddenLayerNum = HiddenLayerSize;
        
        
        // creating the Required amount of matrixes for the Weights
        
        
        // Weights from the Input Layer to the first Hidden Layer
        ILWeights = new int[InputLayerSize][HiddenLayerSize];
        
        // Initializing ILWeights with random values
        for(i = 0;i < InputLayerSize;i++)
        {
            for(j=0;j< HiddenLayerSize;j++)
            {
                ILWeights[i][j] = generateRandomInteger(0,1);
            }
        }
        
        // Weights between Hidden Layers, if any
        if( HiddenLayerNum >= 1)
        {
            HLWeights = new int[HiddenLayerNum-1][HiddenLayerSize][HiddenLayerSize];
             // Initializing HLWeights with random values
            for( i = 0;i < HiddenLayerNum-1;i++)
            {
                for(j=0;j< HiddenLayerSize;j++)
                {
                    for(k=0;k< HiddenLayerSize;k++)
                    {
                        HLWeights[i][j][k] = generateRandomInteger(0,1);
                    }
                }
            }
        }
        
        // Weights from the Last Hidden Layer to the Output Layer
        OLWeights = new int[HiddenLayerSize][OutputLayerSize];
        
        // Initializing OLWeights with random values
        for( i = 0;i < HiddenLayerSize;i++)
        {
            for(j=0;j< OutputLayerSize;j++)
            {
                OLWeights[i][j] = generateRandomInteger(0,1);
            }
        }
        
    }
    
    
    // Method to compute an output given input
    public static int[] compute(int[] Input)
    {
        // variable for cicle control
        int i,j,k;
        
        //flag to know how to multiply in the later belows
        boolean HiddenLayerFlag = false;
        if(HiddenLayerNum >= 1)
        {
            HiddenLayerFlag = true;
        }
        
        // variable to check if the input is the proper size
        int aux;
        
        // variables to get keep the result of each node's calculation
        int zin[] = new int[HiddenLayerSize];
        int zhidden[][] = new int[HiddenLayerNum-1][HiddenLayerSize];
        int zout[] = new int[OutputLayerSize];
        
        // variables to keep the result of each node's output
        int ain[] = new int[HiddenLayerSize];
        int ahidden[][] = new int[HiddenLayerNum-1][HiddenLayerSize];
        int aout[] = new int[OutputLayerSize];

        
        // checking if the input is of the correct size
        try
        {
            for(i = 0;i < InputLayerSize;i++)
            {
               aux = Input[i];
            }
            
        } 
        catch(ArrayIndexOutOfBoundsException e)
        { 
            aout[0] = -1;
            return aout;
        }
    
        // multiplying the input by the input Layer Weights
        for(i=0;i < HiddenLayerSize;i++)
        {
            for(j=0;j < InputLayerSize;j++)
            {
                zin[i] += Input[j]*ILWeights[i][j];
            }
            // activation function
            ain[i] = activation(zin[i]);
        }
        
        
        // cicle to propagate between the Hidden Layers if any
        
        
        // activation function
        
        
        // Multiplying the input by the output Layer Weights
        for(i=0;i < OutputLayerSize;i++)
        {
            for(j=0;j < HiddenLayerSize;j++)
            {
                if(HiddenLayerFlag)
                {
                     zout[i] += ahidden[HiddenLayerNum-1][j]*OLWeights[i][j];
                }
                else
                {
                    zout[i] += ain[j]*OLWeights[i][j];
                }
            }
            // activation function
            ain[i] = activation(zin[i]);
        }
        //activation function
        
        return aout;
    }
    
    
    // Method to apply the activation function
    // we chose the activation function to be 1/1-e^x
    public static int activation(int x)
    {
        return (int) (1/(1-Math.exp(x)));
    }
    
    
    // method to generate random numbers in a range to initialize the Weights
    public static int generateRandomInteger(int min, int max) 
    {
        SecureRandom rand = new SecureRandom();
        rand.setSeed(new Date().getTime());
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
