<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="jakarta.tags.core"%>

<div class="col-md-9 col-sm-9">
    <h1>Register</h1>
    <div class="content-form-page">
        <div class="row">
            <div class="col-md-7 col-sm-7">
                <form action="${pageContext.request.contextPath}/auth/register" 
                      method="post" class="form-horizontal form-without-legend" role="form">

                    <div class="form-group">
                        <label for="email" class="col-lg-4 control-label">Email <span class="require">*</span></label>
                        <div class="col-lg-8">
                            <input type="email" class="form-control" id="email" name="email" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="userName" class="col-lg-4 control-label">Username <span class="require">*</span></label>
                        <div class="col-lg-8">
                            <input type="text" class="form-control" id="userName" name="userName" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="fullName" class="col-lg-4 control-label">Full Name <span class="require">*</span></label>
                        <div class="col-lg-8">
                            <input type="text" class="form-control" id="fullName" name="fullName" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="phone" class="col-lg-4 control-label">Phone <span class="require">*</span></label>
                        <div class="col-lg-8">
                            <input type="text" class="form-control" id="phone" name="phone" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password" class="col-lg-4 control-label">Password <span class="require">*</span></label>
                        <div class="col-lg-8">
                            <input type="password" class="form-control" id="password" name="password" required>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-8 col-md-offset-4 padding-left-0 padding-top-20">
                            <button type="submit" class="btn btn-primary">Register</button>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-8 col-md-offset-4 padding-left-0 padding-top-10 padding-right-30">
                            <hr>
                            <p>Already have an account? <a href="${pageContext.request.contextPath}/auth/login">Login here</a></p>
                        </div>
                    </div>

                    <c:if test="${not empty alert}">
                        <div class="row">
                            <div class="col-lg-8 col-md-offset-4 padding-left-0 padding-top-10">
                                <div class="alert alert-danger">${alert}</div>
                            </div>
                        </div>
                    </c:if>

                </form>
            </div>

            <div class="col-md-4 col-sm-4 pull-right">
                <div class="form-info">
                    <h2><em>Important</em> Information</h2>
                    <p>Duis autem vel eum iriure at dolor vulputate velit esse vel molestie at dolore.</p>
                    <button type="button" class="btn btn-default">More details</button>
                </div>
            </div>

        </div>
    </div>
</div>
