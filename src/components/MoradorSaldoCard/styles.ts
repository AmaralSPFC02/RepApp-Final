import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
  container: {
    flexDirection: "row",
    alignItems: "center",
    gap: 10,
    paddingVertical: 4,
  },

  avatar: {
    width: 40,
    height: 40,
    borderRadius: 20,
  },

  info: {
    flex: 1,
  },

  nome: {
    fontWeight: "500",
    fontSize: 16,
  },

  sub: {
    color: "#777",
  },

  valor: {
    fontWeight: "600",
    fontSize: 15,
  },
});
