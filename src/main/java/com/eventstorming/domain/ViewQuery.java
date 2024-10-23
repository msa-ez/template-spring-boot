forEach: View
fileName: {{namePascalCase}}Query.java
path: {{boundedContext.name}}/{{{options.packagePath}}}/domain
except: {{#checkExtend this}}{{/checkExtend}}
---
package {{options.package}}.domain;

import java.util.Date;
import lombok.Data;

@Data
public class {{namePascalCase}}Query {

    {{#queryParameters}}
    private {{#checkClassName className}}{{/checkClassName}} {{nameCamelCase}};
    {{/queryParameters}}
}
<function>
window.$HandleBars.registerHelper('checkExtend', function (view) {
    if(view.queryParameters =='' || view.dataProjection == 'cqrs' ){
        return true;
    }else{
        return false;
    }
});

window.$HandleBars.registerHelper('checkClassName', function (className) {
    var less = "<";
    var greater = ">";
    if(className.includes("List")){
        var value = className[className.find('<')+1:className.find('>')]
        return "List" + less + value + greater;
    }else{
        return className;
    }
});
</function>