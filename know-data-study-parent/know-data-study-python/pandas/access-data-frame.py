import pandas as pd
import numpy as np

# 生成DataFrame
df = pd.DataFrame(np.arange(30).reshape((6, 5)),
                    columns=['A', 'B', 'C', 'D', 'E'])
print(df)

# 写入本地
# df.to_excel("D:\\实验数据\\data.xls", sheet_name="data")

# ############# 按列访问
# 通过列名访问
# print("column=A: `df['A']`\n", df['A'])
## 访问多个列
# print("column=A,B: `df['A', 'B']`\n", df['A', 'B']) # 错误语法
# print("column=A,B: `df[['A', 'B']]`\n", df[['A', 'B']]) # 正确语法

# 通过属性访问
# print("column=B: `df.B`\n", df.B)
#
# # 通过 .loc[] 访问列
# print("column=A: `df.loc[:, 'A']`\n", df.loc[:, 'A'])
#
# # 通过 .iloc[] 访问列
# print("column=A: `df.iloc[:, 0]`\n", df.iloc[:, 0])  # 假设 'A' 是第一列
#
# # 访问单个元素
# print("`df['B'][0]`:",df['B'][0])
# print("`df.loc[ 0, \"B\"]`:", df.loc[ 0, "B"])

# ############# 按行访问
# 通过 .loc[] 访问行 by 行索引名/标签 (可自定义，默认是数值)
print("row=2: `df.loc[1]`\n", df.loc[1]) # 访问第2行 | df.loc[1] 等效于 df.loc[1, :]
#print("row=2: `df.loc[1, :]`\n", df.loc[1, :])
print("df.loc[1:2]:\n", df.loc[1:2])  # 选择第二到第三行（按标签）

# 通过 .iloc[] 访问列 by 行下标(位置)
print("row=2: `df.iloc[1]`\n", df.iloc[1])
# print("row=2: `df.iloc[1, :]`\n", df.iloc[1, :]) # 访问第2行 | df.iloc[1] 等效于 df.iloc[1, :]
print("df.iloc[1:3]:\n", df.iloc[1:3])  # 选择第二到第三行（按位置）

# 访问单个元素
print("`df.loc[ 0, \"B\"]`:", df.loc[ 0, "B"])
print("`df['B'][0]`:",df['B'][0])

# ######### 读取指定区域 & 根据条件读取
# 读取第1行到第3行，第B列到第D列这个区域内的值
print("\n`df.loc[ 1:3, \"B\":\"D\"]`\n", df.loc[ 1:3, "B":"D"])
# 按index和columns进行切片操作 : 读取第2、3行(即 [1+1,3+1)，第3、4列(即: [2+1, 4+1) )
print("`df.iloc[1:3, 2:4]`:\n", df.iloc[1:3, 2:4])

# df.loc[ df.B > 6] 等价于 df[df.B > 6]
print("`df.loc[ df.B > 6]`:\n", df.loc[ df.B > 6]) # 读取第B列中大于6的值

# 切片操作 : 选择B，C，D，E四列区域内，B列大于6的值
print("`df.loc[ df.B >6, [\"B\",\"C\",\"D\",\"E\"]]`:\n", df.loc[ df.B > 6, ["B","C","D","E"]])
