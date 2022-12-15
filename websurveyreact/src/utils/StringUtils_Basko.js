export function containsCharacters(str, characters) {
  for (let i = 0; i < str.length; i++) {
    if (!characters.includes(str.charAt(i))) {
      return true;
    }
  }
  return false
}