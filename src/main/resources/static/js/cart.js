let index = {
    init:function(){
        $("#btn-cart-put").on("click",()=>{
            this.put();
        });
    },

    put: function(){
        let productId=$("#productId").val();
        let data = {
            productSize:$("#productSize").val(),
            productColor:$("#productColor").val()
        };
        console.log(data)
        $.ajax({
            type:"GET",
            url:"/basket",
            data:JSON.stringify(data),
            contentType:"application/json;charset=utf-8",
            dataType:"json"
        }).done(function(resp){
            alert("장바구니에 담았습니다.");
            location.href="/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
}

index.init();