var main={
    init: function(){
        var _this = this;
        $('#btn-save').on('click',function(){
            _this.save();
        });
        $('#btn-update').on('click',function(){
            _this.update();
        });
        $('#btn-delete').on('click',function(){
            _this.delete();
        });
        $('#btn-point').on('click',function(){
            _this.point();
        });
        $('#btn-buy').on('click',function(){
            if(parseInt($('#point').val())>=parseInt($('#price').val())){
                _this.buy();
            }
            else{
                alert("포인트가 부족해 구매할 수 없습니다.");
            }
        });
        $('#btn-deal').on('click',function(){
            _this.deal();
        });

    },
    save: function(){
        var data={
            title: $('#title').val(),
            content: $('#content').val(),
            author: $('#author').val(),
            price: $('#price').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 등록되었습니다.');
            window.location.href='/';
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
    update: function(){
        var data={
            title: $('#title').val(),
            content: $('#content').val(),
            price: $('#price').val()
        };
        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 수정되었습니다.');
            window.location.href='/';
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
    delete: function(){
        var id=$('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function(){
            alert('글이 삭제되었습니다.');
            window.location.href='/';
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
    point: function(){
        var id=$('#id').val();
        var point=parseInt($('#depositPoint').val())+parseInt($('#point').val());

        $.ajax({
               type: 'PUT',
               url: '/api/point/'+id,
               dataType: 'json',
               contentType:'application/json; charset=utf-8',
               data: point.toString(),
               }).done(function(){
                    alert('수정되었습니다.');
                    window.location.href='/';
               }).fail(function(error){
                    alert(JSON.stringify(error));
               });
    },
    buy: function(){
        var id=$('#userId').val();
        var data={
            postId: $('#postId').val(),
            userId: $('#userId').val()
        };
        var balance=parseInt($('#point').val())-parseInt($('#price').val());

        $.ajax({
              type: 'POST',
              url: '/api/buy/'+id+'/'+balance.toString(),
              dataType: 'json',
              contentType:'application/json; charset=utf-8',
              data: JSON.stringify(data)
        }).done(function(){
              alert('구매 신청 완료.');
              window.location.href='/';
        }).fail(function(error){
              alert(JSON.stringify(error));
        });
    },
    deal: function(){
        var userid=$('#id').val();
        var postid=$('#postid').val();
        var balance=parseInt($('#point').val())+parseInt($('#price').val());

        $.ajax({
                type: 'PUT',
                url: '/api/deal/'+postid+'/'+userid,
                dataType: 'json',
                contentType:'application/json; charset=utf-8',
                data: balance.toString(),
        }).done(function(){
                window.location.href='/';
        }).fail(function(error){
                alert(JSON.stringify(error));
        });
    }
};

main.init();