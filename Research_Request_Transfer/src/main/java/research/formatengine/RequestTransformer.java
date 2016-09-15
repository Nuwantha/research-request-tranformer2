package research.formatengine;

import research.annotation.Format;
import research.models.Request;
import research.models.Specification;
import research.models.SpecificationItem;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by akila on 8/31/16.
 */
public class RequestTransformer {

    private Specification specification;

    public RequestTransformer(){}
    public RequestTransformer(Specification specification){
        this.specification = specification;
    }



    public HashMap<String, Object> transform(HashMap<String,Object> requestBody){

        Iterator iterator=requestBody.entrySet().iterator();
        HashMap<String,Object> request = new HashMap<String, Object>();

        ArrayList<SpecificationItem> items = this.specification.getList();

        class FindFormattingElement{
            private ArrayList<SpecificationItem> itemlist;
            public FindFormattingElement(ArrayList<SpecificationItem> itemlist){this.itemlist = itemlist;}
            public SpecificationItem find(String key){
                Iterator it =itemlist.iterator();

                while (it.hasNext()){
                    SpecificationItem specificationItem = (SpecificationItem)it.next();
                    if(key.equals(specificationItem.getKey())){
                        return specificationItem;
                    }

                }

                return null;
            }
        }


        FindFormattingElement find = new FindFormattingElement(items);
        while (iterator.hasNext()){
            Map.Entry pair = (Map.Entry) iterator.next();
            SpecificationItem specificatinItem = find.find((String) pair.getKey());
            //request.put((String) pair.getKey(),pair.getValue());
            request.put((String) this.elementTransform(pair.getKey(),specificatinItem,true),this.elementTransform(pair.getValue(),specificatinItem,false));
        }
        return request;
    }


    private Object elementTransform(Object element,SpecificationItem specificationItem,boolean isKey){

        if (isKey) {
                return this.actualElementTransform("String", element, specificationItem.getKeyFormatter());
            } else {

                return this.actualElementTransform(specificationItem.getValueType(), element, specificationItem.getValueFormatter());
            }

    }

    private Object actualElementTransform(String type,Object element,String formattingpatttern){
        if (type.equals("String")) {

            String stringItem = (String) element;
            Class cls = null;
            try {
                cls = Class.forName("research.formatengine.StringFormatter");
                StringFormatter formatter = (StringFormatter) cls.newInstance();
                element =this.selectMethod(formatter, formattingpatttern).getName();
                return this.selectMethod(formatter, formattingpatttern).invoke(formatter, stringItem);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return element;
        }else if( type.equals("Date")) {
            DateFormatter dateFormatter = new DateFormatter(formattingpatttern);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parse = simpleDateFormat.parse((String) element);
                String format = dateFormatter.format(parse);
                return format;
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return element;
        }else if(type.equals("Nested")){
                RequestTransformer transformer = new RequestTransformer(this.specification);
            HashMap<String, Object> map = (HashMap<String, Object>) element;
                return transformer.transform(map);
        }

        return element;
    }


    private Method selectMethod(StringFormatter formatter,String format){

        try {
            Class cls = Class.forName("research.formatengine.StringFormatter");
            Object obj =cls.newInstance();
            Method[] methods =cls.getDeclaredMethods();
            for (Method method:methods) {
                Format annotation = method.getAnnotation(Format.class);
                String s = annotation.methodTask();
                if(s.equals(format)){
                    return method;
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}