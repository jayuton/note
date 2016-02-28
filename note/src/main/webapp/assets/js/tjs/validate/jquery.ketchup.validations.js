jQuery.ketchup.validation("required","This field is required.",function(e,t,n){var r=t.attr("type").toLowerCase();return r=="checkbox"||r=="radio"?t.attr("checked")==1:$.trim(n).length!=0}).validation("minlength","This field must have a minimal length of {arg1}.",function(e,t,n,r){return n.length>=+r}).validation("maxlength","This field must have a maximal length of {arg1}.",function(e,t,n,r){return n.length<=+r}).validation("rangelength","This field must have a length between {arg1} and {arg2}.",function(e,t,n,r,i){return n.length>=r&&n.length<=i}).validation("mask","This field must mask pattern {arg2} .",function(e,t,n,r){return this.mask(r,n)}).validation("min","Must be at least {arg1}.",function(e,t,n,r){return this.isNumber(n)&&+n>=+r}).validation("max","Can not be greater than {arg1}.",function(e,t,n,r){return this.isNumber(n)&&+n<=+r}).validation("range","Must be between {arg1} and {arg2}.",function(e,t,n,r,i){return this.isNumber(n)&&+n>=+r&&+n<=+i}).validation("number","Must be a number.",function(e,t,n){return this.isNumber(n)}).validation("digits","Must be digits.",function(e,t,n){return/^\d+$/.test(n)}).validation("email","Must be a valid E-Mail.",function(e,t,n){return this.isEmail(n)}).validation("url","Must be a valid URL.",function(e,t,n){return this.isUrl(n)}).validation("username","Must be a valid username.",function(e,t,n){return this.isUsername(n)}).validation("match","Must be {arg1}.",function(e,t,n,r){return t.val()==r}).validation("contain","Must contain {arg1}",function(e,t,n,r){return this.contains(n,r)}).validation("date","Must be a valid date.",function(e,t,n){return this.isDate(n)}).validation("minselect","Select at least {arg1} checkboxes.",function(e,t,n,r){return r<=this.inputsWithName(e,t).filter(":checked").length},function(e,t){this.bindBrothers(e,t)}).validation("maxselect","Select not more than {arg1} checkboxes.",function(e,t,n,r){return r>=this.inputsWithName(e,t).filter(":checked").length},function(e,t){this.bindBrothers(e,t)}).validation("rangeselect","Select between {arg1} and {arg2} checkboxes.",function(e,t,n,r,i){var s=this.inputsWithName(e,t).filter(":checked").length;return r<=s&&i>=s},function(e,t){this.bindBrothers(e,t)});