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
    if(className.includes("List")){
        return className;
    }else{
        return className;
    }
});
</function>