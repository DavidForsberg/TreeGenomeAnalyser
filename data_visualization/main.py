import matplotlib.pyplot as plt
import pandas as pd

component_size_distribution = pd.read_csv("component_size_distribution.csv")
node_degree_distribution = pd.read_csv("node_degree_distribution.csv")

component_size_distribution = component_size_distribution.set_index("size")
node_degree_distribution = node_degree_distribution.set_index("degree")

component_size_distribution["amount"].sort_values('index', ascending=False).iloc[0:15].plot(kind='bar')
plt.ylabel("# of components")
plt.legend()
plt.show()
node_degree_distribution["amount"].sort_values('index', ascending=False).iloc[0:10].plot(kind='bar')
plt.ylabel("# of nodes")
plt.legend()
plt.show()