console.log("Reply Module.......");

var replyService = (function(){
	
	function add(reply,callback){
		console.log("reply.......");
	
	 $.ajax({
		 type : 'post', //데이터 전송방식
	     url : '/replies/new', // 비동기통신할 주소
         data : JSON.stringify(reply), //data= json을 문자열로 바꿔서 서버로 전달.
         contentType : "application/json; charset=utf-8",
         success : function(result, status, xhr) {
	     //통신성공시 실행되는 함수 아마 result가 서버측에서 전송한데이터 = 서버에서 전송한 json 데이터
	         if(callback) {
		        callback(result); // 그냥 위의 콜백함수 인자에 들어가 클라로 응답된다고 생각하련다
	         }
         },
         error : function(xhr,status, er) {
	        if(error){
		       error(er)
	        }
         }
	  })
	}
	
	function getList(param, callback, error){
		
		var bno = param.bno;
		var page = param.page || 1;
		
		$.getJSON("/replies/pages/" +bno+"/"+page+".json",
		function(data){
			if(callback){
				//callback(data); 댓글목록만 가져오는 경우
				callback(data.replyCnt,data.list);
			}//서버통신결과인 data값에서 댓글의 갯수와 댁슬의 리스트를 페이징처리를 위해 가져옴
		}).fail(function(xhr, status, err) {
			if(error){
				error();
			}
		});
	}
	
	function remove(rno, callback, error){
		$.ajax({
			type : 'delete',
			url : '/replies/' + rno,
			success : function(deleteResult, status, xhr){
				if(callback){
					callback(deleteResult);//쉽게 생각하자 위의 url로 컨트롤러에서 디비까지 갔다온 데이터가 deleteResult값임
					//그걸 출력해주는게 callback임.
				}
			},
			error : function(xhr, status, er) {
				if(error){
					error(er);
				}
			}
		});
	}
	
	function update(reply, callback, error){
		
		console.log("RNO: " + reply.rno);
		
		$.ajax({
			type : 'put',
			url : '/replies/' + reply.rno, 
			data : JSON.stringify(reply),
			contentType : "application/json; charset=utf-8",
			success : function(result, status, xhr) {
				if(callback){
					callback(result);
				}
			 },
		    error : function(xhr, status, er) {
			   if(error){
				 error(er);
			   }
		    }
		});
	}
			
	function get(rno, callback, error){
	
		
		$.get("/replies/" +rno+ ".json",function(result){
			if(callback){
				callback(result);
			}
		}).fail(function(xhr, status, err) {
			if(error){
				error();
			}
		});
	}
	
	function displayTime(timeValue) {

		var today = new Date();

		var gap = today.getTime() - timeValue;

		var dateObj = new Date(timeValue);
		var str = "";

		if (gap < (1000 * 60 * 60 * 24)) {

			var hh = dateObj.getHours();
			var mi = dateObj.getMinutes();
			var ss = dateObj.getSeconds();

			return [ (hh > 9 ? '' : '0') + hh, ':', (mi > 9 ? '' : '0') + mi,
					':', (ss > 9 ? '' : '0') + ss ].join('');

		} else {
			var yy = dateObj.getFullYear();
			var mm = dateObj.getMonth() + 1; // getMonth() is zero-based
			var dd = dateObj.getDate();

			return [ yy, '/', (mm > 9 ? '' : '0') + mm, '/',
					(dd > 9 ? '' : '0') + dd ].join('');
		}
	};
	

	
  return {
	add:add,
	getList : getList,
	remove : remove,
	update : update,
	get : get,
	displayTime : displayTime
	};
})();