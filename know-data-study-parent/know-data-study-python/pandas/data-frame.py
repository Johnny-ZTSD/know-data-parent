import pandas as pd

# 创建 DataFrame
data = {
    'Name': ['Alice', 'Bob', 'Charlie', 'David'],
    'Age': [25, 30, 35, 40],
    'City': ['New York', 'Los Angeles', 'Chicago', 'Houston']
}
df = pd.DataFrame(data)

print("df:\n", df)

# 选择指定列
#print("Name, Age:\n", df[['Name', 'Age']])
print("Name, Age:\n", df.loc['Name' , 'Age'])

# 按索引选择行
# print("df.iloc[1:3]:\n", df.iloc[1:3])  # 选择第二到第三行（按位置）

# 按标签选择行 | loc方法是通过行、列的名称或者标签来寻找我们需要的值
# print("df.loc[1:2]:\n", df.loc[1:2])  # 选择第二到第三行（按标签）
