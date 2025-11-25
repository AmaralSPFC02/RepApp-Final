import { StyleSheet } from "react-native";
import { themas } from "../../global/themes";

export default StyleSheet.create({
  header: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
    paddingHorizontal: 16,
    paddingTop: 10,
    paddingBottom: 14,
  },

  title: {
    fontSize: 22,
    fontWeight: "600",
    color: "#000",
  },

  addBtn: {
    backgroundColor: themas.colors.primary,
    padding: 6,
    borderRadius: 10,
    overflow: "hidden",
  },
});
