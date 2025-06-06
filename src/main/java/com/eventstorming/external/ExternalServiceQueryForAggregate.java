forEach: Relation
fileName: {{target.aggregate.namePascalCase}}Service.java
path: {{source.boundedContext.name}}/{{options.packagePath}}/external
except: {{contexts.except}}
---

package {{options.package}}.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;


{{#if value.fallback}}
@FeignClient(name = "{{target.boundedContext.name}}", url = "{{apiVariable target.boundedContext.name}}", fallback = {{target.aggregate.namePascalCase}}ServiceImpl.class)
{{else}}
@FeignClient(name = "{{target.boundedContext.name}}", url = "{{apiVariable target.boundedContext.name}}")
{{/if}}
 
{{#target}}
public interface {{aggregate.namePascalCase}}Service {
    {{#if queryOption.multipleResult}}
    {{#if queryOption.useDefaultUri}}
    @GetMapping(path="/{{aggregate.namePlural}}/search/{{#if queryOption.apiPath}}{{#firstCamel queryOption.apiPath}}{{/firstCamel}}{{else}}{{nameCamelCase}}{{/if}}")
    public List<{{aggregate.namePascalCase}}> {{nameCamelCase}}({{namePascalCase}}Query {{nameCamelCase}}Query);
    {{else}}
    @GetMapping(path="/{{aggregate.namePlural}}/{{#if queryOption.apiPath}}{{#firstCamel queryOption.apiPath}}{{/firstCamel}}{{else}}{{nameCamelCase}}{{/if}}")
    public List<{{aggregate.namePascalCase}}> {{#if queryOption.apiPath}}{{#firstCamel queryOption.apiPath}}{{/firstCamel}}{{else}}{{nameCamelCase}}{{/if}}({{namePascalCase}}Query {{nameCamelCase}}Query);
    {{/if}}
    {{else}}
    {{#if queryOption.useDefaultUri}}
    @GetMapping(path="/{{aggregate.namePlural}}/{{#aggregateRoot.fieldDescriptors}}{{#if isKey}}{{#addMustache nameCamelCase}}{{/addMustache}}{{/if}}{{/aggregateRoot.fieldDescriptors}}")
    public {{aggregate.namePascalCase}} {{nameCamelCase}} (@PathVariable {{#aggregateRoot.fieldDescriptors}}{{#if isKey}} ("{{nameCamelCase}}") {{className}} {{nameCamelCase}});{{/if}}{{/aggregateRoot.fieldDescriptors}}
    {{else}} 
    @GetMapping(path="/{{aggregate.namePlural}}/{{nameCamelCase}}/{{#aggregateRoot.fieldDescriptors}}{{#if isKey}}{{#addMustache nameCamelCase}}{{/addMustache}}{{/if}}{{/aggregateRoot.fieldDescriptors}}")
    public {{aggregate.namePascalCase}} {{nameCamelCase}} (@PathVariable {{#aggregateRoot.fieldDescriptors}}{{#if isKey}} ("{{nameCamelCase}}") {{className}} {{nameCamelCase}});{{/if}}{{/aggregateRoot.fieldDescriptors}}
    {{/if}}
    {{/if}}

}

{{/target}}



<function>
 

    let isGetInvocation = ((this.source._type.endsWith("Command") || this.source._type.endsWith("Policy")) && (this.target._type.endsWith("View") && this.target.dataProjection==("query-for-aggregate")))
    let isPostInvcation = ((this.source._type.endsWith("Event") || this.source._type.endsWith("Policy")) && this.target._type.endsWith("Command"))
    let isExternalInvocation = (this.source.boundedContext.name != this.target.boundedContext.name)

    this.contexts.except = !(isExternalInvocation && isGetInvocation)
 
if(!this.contexts.except){
 
}
window.$HandleBars.registerHelper('firstCamel', function (name) {
    if (!name || typeof name !== 'string') return '';
    return name.charAt(0).toLowerCase() + name.slice(1);
  });

window.$HandleBars.registerHelper('changeUpper', function (name) {
    return name.charAt(0).toUpperCase() + name.slice(1);
});

window.$HandleBars.registerHelper('addMustache', function (name) {
    var keyName = ''
    keyName = "{" + name + "}"
    return keyName
});

window.$HandleBars.registerHelper('checkKeyParameter', function (view) {
    var idType = ""
    for( var i = 0; i < view.queryParameters.length; i++){
        if(view.queryParameters[i].isKey){
            idType = `("${view.queryParameters[i].nameCamelCase}") ` + view.queryParameters[i].className + " " + view.queryParameters[i].nameCamelCase
            
        }else{
            idType = "(id) " + view.aggregate.keyFieldDescriptor.className + " " + view.aggregate.keyFieldDescriptor.nameCamelCase
            
        }
        return idType
    }
});
 
</function>
