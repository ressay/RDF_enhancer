import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;


public class Converter
{
    ResultSet resultSet=null;
    String url=null;
    
    private static class Needed
    {
        String var;
        LinkedList<String> dictionnary;
        
        Applied app(QuerySolution sol)
        {
            String s = sol.get(var).toString();
            LinkedList<String> fo=new LinkedList<String>();
            for(int i=0;i<dictionnary.size();i++)
                if(s.contains(dictionnary.get(i)))
                    fo.add(dictionnary.get(i));
            
            
            if(!fo.isEmpty())
            return new Applied(var,fo);
            else
            return null;
        }
        Needed(String v,LinkedList<String> dic)
        {
            var=v;
            dictionnary=dic;
        }
        
    }
    
    private static class Applied
    {
        String var;
        LinkedList<String> found;
        
        Applied(String v,LinkedList<String> dic)
        {
            var=v;
            found=dic;
        }
    }
    
    private static class WriterResource
    {
        QuerySolution QS;
        LinkedList<Needed> found;
        
        WriterResource(QuerySolution v,LinkedList<Needed> dic)
        {
            QS=v;
            found=dic;
        }
    }
        
    public Converter(String u)
    {
        url=u;
    }
    
    LinkedList<Applied> applies(QuerySolution sol,LinkedList<Needed> n)
    {
        LinkedList<Applied> aps = new LinkedList<Applied>();
        for(int i=0;i<n.size();i++)
        {
            Applied a = n.get(i).app(sol);
            if(a!=null)
            {
                aps.add(a);
            }
        }
        if(aps.isEmpty())
        return null;
        else
        return aps;
    }
    
    
    void write(WriterResource wr,PrintWriter w, String writer)
    {
        Iterator<String> it = wr.QS.varNames();
        while(it.hasNext())
        w.write(it.next() + "\t");
        w.write("\n");
        
    }
    File deduce(ResultSet r,LinkedList<Needed> n,String writer) throws FileNotFoundException
    {
       File file =new File("tempo.txt");
        PrintWriter w=new PrintWriter(file);
        
        LinkedList<WriterResource> res = new LinkedList<WriterResource>();
        while(r.hasNext())
        {
            QuerySolution sol = r.next();
            if(applies(sol,n) != null)
            {
                res.add(new WriterResource(sol, n));
            }
        }
        if(res.isEmpty())
        return null;
        
        for(int i=0;i<res.size();i++)
        {
            write(res.get(i),w,writer);
        }
        return file;
    }
    
        File deduce(String query,LinkedList<Needed> n,String writer) throws FileNotFoundException
    {
       
        QueryExecution qexec;
        qexec = QueryExecutionFactory.sparqlService(url, query);
        ResultSet results = qexec.execSelect();
        return deduce(results,n,writer);
    }
    
    

    
}
