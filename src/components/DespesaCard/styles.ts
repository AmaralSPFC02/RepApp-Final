import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
  card: {
    backgroundColor: "#FFF",
    padding: 16,
    borderRadius: 12,
    marginBottom: 16,
  },

  row: {
    flexDirection: "row",
    justifyContent: "space-between",
  },

  title: {
    fontWeight: "600",
    fontSize: 16,
  },

  value: {
    fontWeight: "700",
    fontSize: 16,
  },

  sub: {
    color: "#777",
    marginTop: 2,
  },

  fileRow: {
    flexDirection: "row",
    alignItems: "center",
    marginTop: 8,
  },

  file: {
    marginLeft: 4,
    color: "#2D6CF6",
    textDecorationLine: "underline",
  },

  divTitle: {
    marginTop: 10,
    fontWeight: "600",
  },

  divRow: {
    flexDirection: "row",
    gap: 10,
    marginTop: 6,
  },
});
