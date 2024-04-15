import { hasOwn } from 'element-ui/src/utils/com.project.util';

export function isVNode(node) {
  return node !== null && typeof node === 'object' && hasOwn(node, 'componentOptions');
};
