const objectArrayUtils = {
  toTree: (arr) => {
    var map = {},
      node,
      tree = [],
      i;

    if (!arr) return tree;
    for (i = 0; i < arr.length; i += 1) {
      map[arr[i].id] = i;
      arr[i].children = [];
    }

    for (i = 0; i < arr.length; i += 1) {
      node = arr[i];

      if (node.parentId && node.parentId > 0) {
        // if you have dangling branches check that map[node.parentId] exists

        arr[map[node.parentId]].children.push(node);
      } else {
        tree.push(node);
      }
    }

    return tree;
  },
  getItemById: (arr, id) => {
    return this.getItemByProp(arr, "id", id);
  },
  getItemByProp: (arr, prop, propValue) => {
    for (let index = 0; index < arr.length; index++) {
      const element = arr[index];
      if (element[prop] === propValue) return element;

      if (element.children)
        return ObjectArrayUtils.getItemByProp(
          element.children,
          prop,
          propValue
        );
    }
  },
  remove: (arr, item) => {
    arr.splice(arr.indexOf(item), 1);
    return arr;
  },
  copy: (arr) => {
    return JSON.parse(JSON.stringify(arr));
  },

  exists: (arr, item, field) => {
    return arr.filter((x) => x[field] === item[field]).length > 0;
  },
};

export default objectArrayUtils;
