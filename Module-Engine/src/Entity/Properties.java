package Entity;

public class Properties<T>
{
    public String NameOfProperty;
    boolean randomInitialize;
    public T Type;
    public int[] range; // range[0] - from , range[1] - to

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public Properties()
    {
      range=new int[2];
      NameOfProperty= new String();


    }
}
