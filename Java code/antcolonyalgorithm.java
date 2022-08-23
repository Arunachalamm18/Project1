package com.java.initialCode;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
public class antcolonyalgorithm  {
	static ArrayList<String> pathnew=new ArrayList<String>();

	public static void main(String[] args) {
		Scanner scan = new Scanner (System.in);
		System.out.println("Enter the Number of Destinations (or) number of Rows");
		
		int n = scan.nextInt();
		
		String pathi="";
		
		float cost[][] = new float[n][n];
		float pheromone[][] = new float[n][n];
		
		HashMap<ArrayList<Integer>, Float> probs ;
		int shortestindex = 0;
		for (int i=1; i<=n; i++) 
		{
			pathi+=i;
		}
		
		permute(pathi.substring(1,pathi.length()),0,pathi.length()-2);
		System.out.println("Enter the Cost matrix row vise of size :"+n+"x"+n);
		
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<n; j++)
			{
				cost[i][j] = scan.nextFloat();
			}
		}
		System.out.println("Enter the Pheromone matrix row vise of size :"+n+"x"+n);
		
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<n; j++)
			{
				pheromone[i][j] = scan.nextFloat();
			}
		}
	
		int combi=pathnew.size();
		int lee=pathnew.get(0).length();
		int[][] val0=new int[combi][lee];
		for(int i=0; i<combi; i++)
		{
			val0[i] = temppath(pathnew.get(i));
		}
		

		float[] Dists=new float[combi];
		for (int i=0; i<combi; i++) 
		{
		Dists[i]= travel(val0[i],cost);
		}
		
		for (int i=0; i<pathnew.size(); i++) {
			System.out.println("Path : "+pathnew.get(i)+" "+"Distance: "+Dists[i]);
		}
		
		float shortest=Dists[0];
		
		for(int i=1; i<Dists.length; i++)
		{
			if(Dists[i]<shortest) {
				shortest=Dists[i];
			}
		}
		System.out.println("shortest Distance is "+shortest);
		
		for(int i=0; i<Dists.length; i++) {
			if (Dists[i]==shortest) {
				shortestindex=i;
			}
		}
		System.out.println("Shortest Path "+ pathnew.get(shortestindex));
		
		float pheromat[][] = pherocal(pheromone, Dists,val0, n);
		System.out.println("Pheromone matrix after mathematical Calculation");
		
		print(pheromat, n);
		
		System.out.println("Probability of Vehicle to take that path");
		 probs=probcal(pheromone, n, cost);
		  //Calculation for greater probablity to choose route
		 
		 ArrayList<Integer> kay =new ArrayList<Integer>();
		   float great;
		   int l=1;
		  
		   ArrayList <Integer> shortestpath = new ArrayList<>();
		   shortestpath.add(1);
		  
		   for (int i=1; i<n; i++) {
		   ArrayList<ArrayList<Integer>> pathcho=new ArrayList<ArrayList<Integer>>();
		  for(Map.Entry<ArrayList<Integer>,Float> entry:probs.entrySet()) 
		  {
		   if(entry.getKey().get(1)==l) 
		   {
		    pathcho.add(entry.getKey());
		   } 
		  }
		  
		  if (pathcho.size()>0) 
		  {
		  kay=new ArrayList<Integer>(Arrays.asList(0,0));
		  great=(float)0;
		  float prev=great;
		
		  ArrayList<Integer>prevkey=new ArrayList<Integer>();
		  for(int m=0; m<=pathcho.size()-1; m++) 
		  {
				if(great<probs.get(pathcho.get(m))) 
				{
					prev=great;
					great=probs.get(pathcho.get(m));
					prevkey=kay;
					kay=pathcho.get(m);
					if(shortestpath.contains(pathcho.get(m).get(0))==true) 
					{
					great=prev;
					kay=prevkey;
					}
				}
		  }
		
		  }
		  l=kay.get(0);
		  shortestpath.add(kay.get(0));
		
		  }
		 System.out.println("Shortest Path is:");
		 for (int i : shortestpath) {
			 System.out.print(i);
		 }


	}

	private static void permute(String str, int l, int e) {
		// TODO Auto-generated method stub
		if (l==e) {
			pathnew.add(1+str);
		}
		else {
			for(int i=l;i<=e;i++) {
				str=swap(str,l,i);
				permute(str,l+1,e);
			}
		}
		
	}
	private static int[] temppath(String str) {
		int[] newGuess = new int[str.length()];
		
		for (int i = 0; i < str.length(); i++)
		{
		    newGuess[i] = str.charAt(i) - '0';
		}
		return newGuess;
		
	}
	private static String swap(String str, int l, int i) {
		// TODO Auto-generated method stub
		char temp;
		char[] charArray=str.toCharArray();
		temp=charArray[l];
		charArray[l]=charArray[i];
		charArray[i]=temp;
		return String.valueOf(charArray);
		
	}
	private static  HashMap<ArrayList<Integer>, Float> probcal(float[][] pheromat, int n, float[][] cost) {
		   
		HashMap<ArrayList<Integer>, Float> probs =new HashMap<ArrayList<Integer>,Float>();
		 for(int i=0; i<n; i++)
		  {
		   float denom = 0;
		   float ans = 0;
		   for(int k=0;k<n;k++)
		   {
			   if(cost[i][k]!=0 || i!=k) {
			   denom += (pheromat[i][k]*(1.0/cost[i][k])); }
		    }
		  
		   for(int j=0; j<n; j++)
		   {
		    if(pheromat[i][j]!=0 &&i!=j)
		    {
		     ans = (float) ((pheromat[i][j]*(1.0/cost[i][j]))/(denom));
		     ArrayList<Integer> a= new ArrayList<Integer>( Arrays.asList(j+1,i+1));
		     probs.put(a, ans);
		     System.out.println("Probability of going to "+(j+1)+" From "+(i+1)+" is: "+ans );
		    }
		    
		    else if(pheromat[j][i]!=0 && i!=j)
		    {
		     ans = (float) ((pheromat[j][i]*(1.0/cost[j][i]))/(denom));
		     ArrayList<Integer> a= new ArrayList<Integer>( Arrays.asList(j+1,i+1));
		     probs.put(a,ans);
		     System.out.println("Probability of going to "+(j+1)+" From "+(i+1)+" is: "+ans );
		    }
		   }
		  }
		   
		  for (Entry<ArrayList<Integer>, Float> set : probs.entrySet()) 
		  {
	            System.out.println(set.getKey().get(0)+" "+set.getKey().get(1) + " = "
	                               + set.getValue());
	      }
		  return probs;
		 }

	private static float[][] pherocal(float[][] pheromone,float[] Dists,int[][] val0, int n) 
	{
		float ans[][] =new float[n][n];
		
		for(int j=0; j <val0.length; j++) {
			for(int i=0; i<val0[0].length-1; i++)
			{
				if(ans[val0[j][i]-1][val0[j][i+1]-1]==0 && ans[val0[j][i+1]-1][val0[j][i]-1]==0)
				{
					ans[val0[j][i]-1][val0[j][i+1]-1] += (1.0/Dists[j]);
				}
				else if(ans[val0[j][i+1]-1][val0[j][i]-1]!=0)
				{
					ans[val0[j][i+1]-1][val0[j][i]-1] += (1.0/Dists[j]);
				}
				else
				{
					ans[val0[j][i]-1][val0[j][i+1]-1] += (1.0/Dists[j]);
				}
			}
		}	

		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(ans[i][j]!=0)
				{
					ans[i][j] += ((1.0)*pheromone[i][j]);
				}
			}
		}
		return ans;
	}

	private static float travel(int[] val0, float[][] cost) {
		float ans=0;
		int len = val0.length;

		for(int i=0; i<len-1; i++)
		{
			ans += cost[val0[i]-1][val0[i+1]-1];
		}
		return ans;
	}
	
	private static void print(float[][] pheromat, int n) {
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<n; j++)
			{
				System.out.printf("%.2f ",pheromat[i][j]);
			}
			System.out.println();
		}
	}
}