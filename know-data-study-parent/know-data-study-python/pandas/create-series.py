import pandas as pd

sites = {1: "Google", 2: "Runoob", 3: "Wiki"}
myvar = pd.Series(sites)
print(myvar)

myvar2 = pd.Series(sites, index = [1, 2])
print(myvar2)

"""
1    Google
2    Runoob
3      Wiki
dtype: object

1    Google
2    Runoob
dtype: object
"""