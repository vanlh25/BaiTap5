<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- BEGIN TOP BAR -->
<div class="pre-header">
    <div class="container">
        <div class="row">
            <!-- BEGIN TOP BAR LEFT PART: Thông tin sinh viên -->
            <div class="col-md-6 col-sm-6 additional-shop-info">
                <ul class="list-unstyled list-inline">
                    <li><i class="fa fa-phone"></i> 085 611 3439</li>
                    <li>Lê Hữu Văn</li>
                    <li>MSSV: 23110171</li>
                    <li>HCMUTE</li>
                </ul>
            </div>
            <!-- END TOP BAR LEFT PART -->

            <!-- BEGIN TOP BAR MENU: Chỉ giữ login/register hoặc profile/logout -->
            <div class="col-md-6 col-sm-6 additional-nav">
                <ul class="list-unstyled list-inline pull-right">
                    <li>
                        <c:choose>				   
                            <c:when test="${sessionScope.account == null}">
                                <a href="${pageContext.request.contextPath}/auth/login">Login</a>
                                | <a href="${pageContext.request.contextPath}/auth/register">Register</a>
                            </c:when>				    
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/profile"> ${sessionScope.account.fullName} </a>
                                | <a href="${pageContext.request.contextPath}/auth/logout">Logout</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </ul>
            </div>
            <!-- END TOP BAR MENU -->
        </div>
    </div>        
</div>
<!-- END TOP BAR -->

<!-- BEGIN HEADER -->
<div class="header">
    <div class="container">
        <a class="site-logo" href="shop-index.html">
            <img src="${URL}assets/frontend/layout/img/logos/logo-shop-red.png" alt="Metronic Shop UI">
        </a>

        <a href="javascript:void(0);" class="mobi-toggler"><i class="fa fa-bars"></i></a>

        <!-- BEGIN CART -->
        <div class="top-cart-block">
            <div class="top-cart-info">
                <a href="javascript:void(0);" class="top-cart-info-count">3 items</a>
                <a href="javascript:void(0);" class="top-cart-info-value">$1260</a>
            </div>
            <i class="fa fa-shopping-cart"></i>
            <div class="top-cart-content-wrapper">
                <div class="top-cart-content">
                    <ul class="scroller" style="height: 250px;">
                        <!-- giữ nguyên list sản phẩm trong giỏ hàng -->
                    </ul>
                    <div class="text-right">
                        <a href="shop-shopping-cart.html" class="btn btn-default">View Cart</a>
                        <a href="shop-checkout.html" class="btn btn-primary">Checkout</a>
                    </div>
                </div>
            </div>            
        </div>
        <!--END CART -->

        <!-- BEGIN NAVIGATION -->
        <div class="header-navigation">
            <ul>
                <!-- giữ nguyên toàn bộ menu navigation -->
            </ul>
        </div>
        <!-- END NAVIGATION -->
    </div>
</div>
<!-- Header END -->
