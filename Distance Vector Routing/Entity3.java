public class Entity3 extends Entity
{    
    int self=3;
    protected int[] directNeighbourDistance= new int[4];
    protected int[] minCost= new int[4];
    // Perform any necessary initialization in the constructor
    public Entity3()
    {
        directNeighbourDistance[0]=7;
        directNeighbourDistance[2]=2;
        
        this.distanceTable[0][2] = 4;
        this.distanceTable[1][2] = 3;
        this.distanceTable[2][3] = 2;
        
        minCost[0] = 4;
        minCost[1] = 3;
        minCost[2] = 2;
        minCost[3] = 0;
        
        Packet toNeighbour_0 = new Packet(3,0,minCost);
        Packet toNeighbour_2 = new Packet(3,2,minCost);
        
        NetworkSimulator.toLayer2(toNeighbour_0);
        NetworkSimulator.toLayer2(toNeighbour_2);
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
        int newMinCst[]=new int[4];
        boolean isChanged=false;
        
        if ((directNeighbourDistance[p_src]!=0) && p_dest==self)
        {
            for(int x=0;x<4;x++)
            {
                newMinCst[x]=p.getMincost(x);
            }
            newMinCst[self]=minCost[self];
            
            for(int x=0;x<4;x++)
            {
                if(x != self)
                {
                    if(minCost[x]>(newMinCst[x]+directNeighbourDistance[p_src]))
                    {
                       minCost[x]=newMinCst[x]+directNeighbourDistance[p_src];
                       this.distanceTable[x][p_src]=minCost[x];
                       isChanged=true;
                    }
                }
            }
            if(isChanged==true)
            {
                Packet toNeighbour_0 = new Packet(3,0,minCost);
                Packet toNeighbour_2 = new Packet(3,2,minCost);
        
                NetworkSimulator.toLayer2(toNeighbour_0);
                NetworkSimulator.toLayer2(toNeighbour_2);
            }
        }
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
    
    public void printDT()
    {
        System.out.println("         via");
        System.out.println(" D3 |   0   2");
        System.out.println("----+--------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
        {
            if (i == 3)
            {
                continue;
            }
            
            System.out.print("   " + i + "|");
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j += 2)
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
