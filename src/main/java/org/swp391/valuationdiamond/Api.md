- Khi tạo mình sẽ kiểm tra xem ở trong database nó đã tồn tại hay chưa, nếu chưa thì mới tạo
- Khi đọc mình sẽ kiểm tra xem nó có tồn tại hay không, nếu không thì trả về lỗi
- Khi update mình sẽ kiểm tra xem nó có tồn tại hay không, nếu không thì trả về lỗi
- Khi xóa mình sẽ kiểm tra xem nó có tồn tại hay không, nếu không thì trả về lỗi

- Khi tạo order mình sẽ tạo cái order tương ứng với request tương ứng



USER:


- Create
  tạo account cho tất cả
  http://localhost:8080/user_request/create
  http://localhost:8080/user_request/login

- Read

- http://localhost:8080/user_request/getUser/{userId}
- http://localhost:8080/user_request/getStaff
- http://localhost:8080/user_requesuser_request/getStafft/getCustomer

- Update
  http://localhost:8080/user_request/updateUser/{userId}
- Delete
  http://localhost:8080/user_request/deleteUser/{userId}


----------------------------------------------
REQUEST:

- Create
  Create http://localhost:8080/evaluation-request/create
- Read
  get request from requestID http://localhost:8080/evaluation-request/{requestId}
  get request with status http://localhost:8080/evaluation-request/list/{status}
  get all requests http://localhost:8080/evaluation-request/gett_all
  get request from userID http://localhost:8080/evaluation-request/get_by_user/{userId}
- Update
- http://localhost:8080/evaluation-request/updateStatus/{requestId}
  http://localhost:8080/evaluation-request/update/{requestId}
- Delete
  http://localhost:8080/evaluation-request/delete/{requestId}
-------------------------------------------------
ORDER:
- Create
- Read
  - 
- Update
- Delete

-------------------------------------------------
EVALUATION RESULT:
- Create
- Read
- Update
- Delete
-------------------------------------------------
Committed paper:
- Create
  http://localhost:8080/committed_Paper/create
- Read
  get all http://localhost:8080/committed_Paper/getCommittedPaper
  get by committedId http://localhost:8080/committed_Paper/getCommittedPaper/{committedId}
  get by userId  http://localhost:8080/committed_Paper/getCommittedPaperByUserId/{userId}
  get by orderId http://localhost:8080/committed_Paper/getCommittedPaperByOrderId/{orderId}




Service Price List
- Create
- Read
- Update
  - Update by Service Id: http://localhost:8080/service_price_list/updateServicePriceList/{serviceId}
  - Update by price list http://localhost:8080/service_price_list/updateServicePriceListById/{id}
- Delete

-------------------------------------------------
Service
- Create
  http://localhost:8080/service/create
- Read
  http://localhost:8080/service/getServiceById/{serviceId}
  http://localhost:8080/service/getServices
- Update
  http://localhost:8080/service/update/{serviceId}
- Delete
