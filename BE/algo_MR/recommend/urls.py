from django.urls import include, path
from rest_framework import routers
from . import views #views.py import

# router = routers.DefaultRouter() #DefaultRouter를 설정
# router.register('BeakjoonUser', views.BeakjoonUserViewSet, basename='beakjoonuser') #itemviewset 과 item이라는 router 등록

urlpatterns = [
    path('test_recommend_problem/', views.test_recommend_problem, name='test_recommend_problem'),
    path('problem_correlation/', views.problem_correlation, name='problem_correlation'),
]