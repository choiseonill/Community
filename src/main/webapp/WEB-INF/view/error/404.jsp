<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>404 error</title>
</head>
<body>
<div class="container">
  <!-- Jumbotron -->
  <div class="jumbotron">
    <h1><i class="fa fa-frown-o red"></i>에러가 발생했습니다.</h1>
    <p class="lead">We couldn't find what you're looking for on <em><span id="display-domain"></span></em>.</p>
    <p><a onclick=javascript:checkSite(); class="btn btn-default btn-lg"><span class="green">Take Me To The Homepage</span></a>
        <script type="text/javascript">
            function checkSite(){
              var currentSite = window.location.hostname;
                window.location = "http://" + currentSite;
            }
        </script>
    </p>
  </div>
</div>
<div class="container">
  <div class="body-content">
    <div class="row">
      <div class="col-md-6">
        <h2>What happened?</h2>
        <p class="lead">A 404 error status implies that the file or page that you're looking for could not be found.</p>
      </div>
      <div class="col-md-6">
        <h2>What can I do?</h2>
        <p class="lead">If you're a site visitor</p>
        <p>Please use your browser's back button and check that you're in the right place. If you need immediate assistance, please send us an email instead.</p>
        <p class="lead">If you're the site owner</p>
         <p>Please check that you're in the right place and get in touch with your website provider if you believe this to be an error.</p>
     </div>
    </div>
  </div>
</div>
</body>
</html>