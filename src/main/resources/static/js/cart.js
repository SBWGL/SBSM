$("#cartPut").click(function(){
            let productId=/*[[${product.productId}]]*/;
            let data = {
                count:$("#count").val(),
                productSize:$("#productSize").val(),
                productColor:$("#productColor").val()
            };
            console.log(data);
            console.log(productId);
            console.log(count);
            $.ajax({
                type:"POST",
                url:"/basket/addBasket",
                data:JSON.stringify(data),
                contentType:"application/json;charset=utf-8",
                dataType:"json"
            }).done(function(resp){
                alert("장바구니에 담았습니다.");
                location.href="/";
            }).fail(function(error){
                alert(JSON.stringify(error));
            });
        });