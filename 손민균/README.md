# 머신러닝학습

## 기본모델 정리
### 1. 회귀분석

- x,y값으로 데이터 학습 → x값 입력시 y값 추측

### 2. 로지스틱회귀

- 유사 회귀분석 판단값이 이진(1,0) 또는 n진 결과는 항상 (0~n-1)

### 3. KNN

- 인접그룹에 포함

### 4. 나이브베이즈

- 자연어처리 가중치 부여 후 해당 내용의 단어들을통해 그룹 판단

### 5. 결정트리

- 규칙을 통한 학습(가지치기), 깊이에 따라 세부도 증가

### 6. 선형판별분석(LDA)

- 그룹을 1차원공간에 projection후 두 데이터 그룹이 잘구분되는지를 판단 이러한 벡터를 구하는것(평균에서 멀고 분산이 작을수록 구분이 잘되어있음)

### 7. LMM

- 언어모델 → 인간의 언어 이해하고 생성하도록 훈련

### 8. 최소제곱법(PLS)

- 여러가지입력변수가 출력변수에 어떤영향을 미치는지
- 노이즈나 상관관계의 복잡성을 정제


## Testing
### 1. KNN
```
import numpy as np
import pandas as pd
from sklearn.neighbors import NearestNeighbors


user_data = np.array([[3, 2],
                      [2, 3],
                      [4, 5],
                      [1, 2],
                      [5, 4],
                      [3, 3],
                      [2, 2],
                      [4, 4],
                      [5, 5],
                      [1, 1]])

# K-NN 모델 훈련
k = 3  # 유저 그룹에서 비슷한 K명을 선택
knn = NearestNeighbors(n_neighbors=k, metric='manhattan')  # 거리 메트릭은 맨하탄 거리를 사용
knn.fit(user_data)

# 추천 대상 유저 선택
target_user = np.array([[3, 3]])  # 추천을 받을 유저의 레벨 (예제로 [3, 3] 선택)

# 유저와 유사한 K명을 찾아서 유저 그룹 형성
distances, neighbor_indices = knn.kneighbors(target_user)

similar_user_levels = user_data[neighbor_indices[0]]
print(f"타겟유저레벨: {target_user}")
print(f"비슷한 유저레벨: {similar_user_levels}")

# 추천 대상 유저와 유사한 레벨의 문제 추출 (가상의 문제 ID)
problem_levels = np.array([1, 2, 3, 4, 5, 1, 2, 3, 4, 5])  # 각 문제의 레벨 (예제용)
recommended_problems = [problem_id for problem_id, problem_level in enumerate(problem_levels, start=1)
                        if problem_level in similar_user_levels[:, 1]]

print(f"추천문제: {recommended_problems}")
```

### 2. 속성별 상관관계 분석
```
import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt

# 가상의 데이터 프레임 생성
data = {
    '속성1': np.random.rand(100),
    '속성2': np.random.rand(100),
    '속성3': np.random.rand(100),
    '속성4': np.random.rand(100),
}

df = pd.DataFrame(data)

# 상관관계 계산
correlation_matrix = df.corr()

# 상관관계 히트맵 생성
plt.figure(figsize=(10, 8))
sns.heatmap(correlation_matrix, annot=True, cmap='coolwarm', vmin=-1, vmax=1)
# plt.title('속성별 상관관계 히트맵')
# plt.show()

# 상관관계 테이블 출력
print(correlation_matrix)
```