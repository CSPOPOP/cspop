<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .navbar-nav {
        display: flex;
        justify-content: flex-end;
    }

    .navbar-nav .nav-item {
        margin-right: 20px;
    }

    .navbar-nav .nav-item:last-child {
        margin-right: 0;
    }

    .navbar-nav .nav-link {
        white-space: nowrap;
    }
</style>
<nav>
    <div class="container-fluid">
        <div class="navbar-collapse">
            <ul class="navbar-nav flex-row">
                <li class="nav-item"><a class="nav-link text-white" href="/api/graduation/graduate_management?page=0&size=10">졸업자 조회</a></li>
                <li class="nav-item"><a class="nav-link text-white" href="/api/graduation/graduate_submitForm?page=0&size=10">신청접수</a></li>
                <li class="nav-item"><a class="nav-link text-white" href="/api/graduation/graduate_proposalForm?page=0&size=10">제안서</a></li>
            </ul>
        </div>
   </div>
</nav>
<nav>
    <div class="container-fluid">
        <div class="navbar-collapse">
            <ul class="navbar-nav flex-row">
                <li class="nav-item"><a class="nav-link text-white" href="/api/graduation/graduate_interimForm?page=0&size=10">중간보고서</a></li>
                <li class="nav-item"><a class="nav-link text-white" href="/api/graduation/graduate_finalForm?page=0&size=10">최종보고서</a></li>
                <li class="nav-item"><a class="nav-link text-white" href="/api/graduation/graduate_otherForm?page=0&size=10">기타자격</a></li>
                <li class="nav-item"><a class="nav-link text-white" href="/api/graduation/graduate_finalPass?page=0&size=10">최종통과</a></li>
            </ul>
        </div>
    </div>
</nav>
