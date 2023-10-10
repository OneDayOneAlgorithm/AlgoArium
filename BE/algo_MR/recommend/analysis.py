# import pandas as pd
# from recommend.models import BeakjoonUser
# from sklearn.preprocessing import StandardScaler
# from sklearn.neighbors import KNeighborsClassifier

# # BeakjoonUser 모델로부터 데이터 가져오기
# beakjoon_users = BeakjoonUser.objects.all()

# # 데이터 프레임 생성
# data = pd.DataFrame(list(beakjoon_users.values( )))
# print(data)

# scaler = StandardScaler()
# data_scaled = scaler.fit_transform(data)
# print(data_scaled)

# # KNN 모델 생성
# k = 3  # 이웃의 수 설정
# knn = KNeighborsClassifier(n_neighbors=k)

# # 특징 (X)과 레이블 (Y) 분리
# X = data_scaled  # 특징
# Y = data['bj_class']  # 레이블 (bj_class)

# # 모델 학습
# knn.fit(X, Y)

# # 새로운 데이터에 대한 예측
# new_data = [[solved_count, tier, rating, rank, bj_class]]  # 분석할 데이터
# new_data_scaled = scaler.transform(new_data)
# prediction = knn.predict(new_data_scaled)