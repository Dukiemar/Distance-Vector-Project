public class Entity0 extends Entity
{    
    //Integer representing the current node
    int self=0;
    //Declaration on an array of integers to hold the the nodes that are neighbours to entity0
    protected int[] directNeighbours= new int [4];
    //Declaration on an array to hold the minimum cost from entity0 to the other entities
    protected int[] minCost= new int[NetworkSimulator.NUMENTITIES];
    // Perform any necessary initialization in the constructor
    public Entity0()
    {
        //Initialize distance table for entity0 to infinity infinity
    	for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
    	{
    		for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++)
    		{
    			distanceTable[i][j] = 999;
    		}
    	}
        //Assignment of the respective neighbouring nodes to the indexes in the directNeighbour array
       
        directNeighbours[1]=1;
        directNeighbours[2]=2;
        directNeighbours[3]=3;
        
        //this is the distance table containing all the values for the distances to immediate neighbours
        this.distanceTable[0][0] = 0;
        this.distanceTable[0][1] = 1;
        this.distanceTable[0][2] = 3;
        this.distanceTable[0][3] = 7;
        //values assigned the the minCost array which repersents the minimum cost to each node
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
    	{
    		int infinity = 999;
    		for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++)
    		{
    			if (distanceTable[i][j] < infinity)
    			{
    				infinity = distanceTable[i][j];
    			}
    		}
    		minCost[i] = infinity;
    	}
        
        /*minCost[0] = 0;
        minCost[1] = 1;
        minCost[2] = 2;
        minCost[3] = 4;
        */
        //assign the packets to be sent to the neighbours 
        Packet toNeighbour_1 = new Packet(0, 1, minCost);
        Packet toNeighbour_2 = new Packet(0, 2, minCost);
        Packet toNeighbour_3 = new Packet(0, 3, minCost);
        //call the toLayer2 function to send each packet
        NetworkSimulator.toLayer2(toNeighbour_1);
        NetworkSimulator.toLayer2(toNeighbour_2);
        NetworkSimulator.toLayer2(toNeighbour_3);
        
        
    }
    
    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {        
        int p_src=p.getSource();
        int p_dest= p.getDest();
        //int newMinCst[]=new int[NetworkSimulator.NUMENTITIES];
        boolean isUpdated=false;
        for(int x = 0; x < minCost.length; x++)
            {
    		   //If the new cost to a node is less than the current, update the distance table
    		    if(distanceTable[x][p_src] > p.getMincost(x) + minCost[p_src])
    		    {
    			   this.distanceTable[x][p_src] = p.getMincost(x) + minCost[p_src];
    			   //Check to see if this new cost is also a minimum cost
    			   if (p.getMincost(x) + minCost[p_src] < minCost[x])
    			   {
    				 minCost[x] = p.getMincost(x) + minCost[p_src];
    				 isUpdated = true;
    			   }
               }
            }
        if(isUpdated==true)
            {
                Packet toNeighbour_1 = new Packet(0, 1, minCost);
                Packet toNeighbour_2 = new Packet(0, 2, minCost);
                Packet toNeighbour_3 = new Packet(0, 3, minCost);
        
                NetworkSimulator.toLayer2(toNeighbour_1);
                NetworkSimulator.toLayer2(toNeighbour_2);
                NetworkSimulator.toLayer2(toNeighbour_3);
        
            }
      }
    
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
    
    public void printDT()
    {
        System.out.println();
        System.out.println("           via");
        System.out.println(" D0 |   1   2   3");
        System.out.println("----+------------");
        for (int i = 1; i < NetworkSimulator.NUMENTITIES; i++)
        {
            System.out.print("   " + i + "|");
            for (int j = 1; j < NetworkSimulator.NUMENTITIES; j++)
            {
                if (distanceTable[i][j] < 10)
                {    
                    System.out.print("   ");
                }
                else if (distanceTable[i][j] < 100)
                {
                    System.out.print("  ");
                }
                else 
                {
                    System.out.print(" ");
                }
                
                System.out.print(distanceTable[i][j]);
            }
            System.out.println();
        }
    }
}
