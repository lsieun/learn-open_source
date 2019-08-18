package lsieun.openvpn;

public class ProcessDemo {
    public static void main(String[] args) throws Exception {

        Runtime r=Runtime.getRuntime();

        System.out.println("No of Processor: "+ r.availableProcessors());
        System.out.println("Total memory: "+r.totalMemory());
        System.out.println("Free memory: "+r.freeMemory());
        System.out.println("Memory occupied: "+ (r.totalMemory()-r.freeMemory()));

        for(int i=0;i<=10000;i++){
            new Object();
        }

        r.gc();

        System.out.println("::Memory status::");
        System.out.println("Total memory: "+r.totalMemory());
        System.out.println("Free memory: "+r.freeMemory());
        System.out.println("Memory occupied: "+
                (r.totalMemory()-r.freeMemory()));
    }
}
