import json, pathlib, struct, zlib, random
root=pathlib.Path("src/main/resources")
mod="scaffolding_ultra"
parts={
"standard":("Stojak pionowy","Vertical standard",[[6,0,6,10,16,10],[3,2,3,13,3,13],[3,8,3,13,9,13],[3,14,3,13,15,13]],[" I "," I "," I "],4),
"ledger":("Rygiel poziomy","Horizontal ledger",[[0,7,6,16,9,10],[0,5,5,2,11,11],[14,5,5,16,11,11]],["III"],4),
"brace":("Stężenie ukośne","Diagonal brace",[[0,0,7,4,4,9],[3,3,7,7,7,9],[6,6,7,10,10,9],[9,9,7,13,13,9],[12,12,7,16,16,9]],["  I"," I ","I  "],4),
"deck":("Podest stalowy","Steel deck",[[0,0,0,16,3,16]],["III","III"],3),
"base_jack":("Podstawka regulowana","Base jack",[[2,0,2,14,2,14],[6,2,6,10,12,10],[4,5,4,12,7,12]],[" I "," N ","III"],2),
"ladder":("Drabina stalowa","Steel ladder",[[2,0,6,4,16,9],[12,0,6,14,16,9],[4,2,6,12,4,9],[4,6,6,12,8,9],[4,10,6,12,12,9],[4,14,6,12,16,9]],["I I","III","I I"],4),
"guardrail":("Poręcz","Guardrail",[[0,5,7,16,7,9],[0,13,7,16,15,9],[0,0,6,2,16,10],[14,0,6,16,16,10]],["III","I I","III"],4),
"stairs":("Schody stalowe","Steel stairs",[[0,0,0,16,4,4],[0,0,4,16,8,8],[0,0,8,16,12,12],[0,0,12,16,16,16]],["I  ","II ","III"],4)}
parts["standard_half"]=("Stojak 0,5 m","Half standard",[[6,0,6,10,8,10],[3,2,3,13,3,13]],["B"],1)
parts["standard_top"]=("Stojak końcowy","Top standard",[[6,0,6,10,16,10],[3,2,3,13,3,13]],["B"],1)
parts["standard_heavy"]=("Stojak ciężki","Heavy standard",[[5,0,5,11,16,11],[2,3,2,14,5,14],[2,11,2,14,13,14]],["B"],1)
parts["standard_extension"]=("Przedłużenie stojaka","Standard extension",[[7,0,7,9,16,9],[5,0,5,11,4,11]],["B"],1)
parts["ledger_half"]=("Rygiel 0,5 m","Half ledger",[[0,7,6,8,9,10]],["B"],1)
parts["ledger_double"]=("Rygiel podwójny","Double ledger",[[0,7,5,16,9,7],[0,7,9,16,9,11]],["B"],1)
parts["ledger_heavy"]=("Rygiel ciężki","Heavy ledger",[[0,6,5,16,10,11]],["B"],1)
parts["ledger_end"]=("Rygiel końcowy","End ledger",[[0,7,6,16,9,10],[12,4,4,16,12,12]],["B"],1)
parts["brace_reverse"]=("Stężenie odwrotne","Reverse brace",[[12,0,7,16,4,9],[9,3,7,13,7,9],[6,6,7,10,10,9],[3,9,7,7,13,9],[0,12,7,4,16,9]],["B"],1)
parts["brace_cross"]=("Stężenie krzyżowe","Cross brace",[[0,0,6,4,4,8],[12,0,8,16,4,10],[3,3,6,7,7,8],[9,3,8,13,7,10],[6,6,6,10,10,8],[6,6,8,10,10,10],[9,9,6,13,13,8],[3,9,8,7,13,10],[12,12,6,16,16,8],[0,12,8,4,16,10]],["B"],1)
parts["deck_half"]=("Podest połówkowy","Half deck",[[0,0,0,16,3,8]],["B"],1)
parts["deck_wood"]=("Podest drewniany","Wood deck",[[0,0,0,16,3,16]],["B"],1)
parts["deck_hatch"]=("Podest z otworem","Hatch deck",[[0,0,0,4,3,16],[12,0,0,16,3,16],[4,0,0,12,3,4],[4,0,12,12,3,16]],["B"],1)
parts["base_high"]=("Podstawka wysoka","High jack",[[2,0,2,14,2,14],[6,2,6,10,16,10],[4,9,4,12,11,12]],["B"],1)
parts["base_low"]=("Podstawka niska","Low jack",[[2,0,2,14,2,14],[6,2,6,10,7,10]],["B"],1)
parts["ladder_wide"]=("Drabina szeroka","Wide ladder",[[0,0,6,2,16,9],[14,0,6,16,16,9],[2,2,6,14,4,9],[2,6,6,14,8,9],[2,10,6,14,12,9],[2,14,6,14,16,9]],["B"],1)
parts["ladder_narrow"]=("Drabina wąska","Narrow ladder",[[4,0,6,6,16,9],[10,0,6,12,16,9],[6,2,6,10,4,9],[6,6,6,10,8,9],[6,10,6,10,12,9],[6,14,6,10,16,9]],["B"],1)
parts["guardrail_single"]=("Poręcz pojedyncza","Single rail",[[0,13,7,16,15,9]],["B"],1)
parts["toe_board"]=("Burta zabezpieczająca","Toe board",[[0,0,7,16,4,9]],["B"],1)
parts["frame"]=("Rama modułowa","Modular frame",[[0,0,6,3,16,10],[13,0,6,16,16,10],[0,12,6,16,15,10]],["B"],1)
parts["frame_cross"]=("Rama ze stężeniem","Braced frame",[[0,0,6,3,16,10],[13,0,6,16,16,10],[0,12,6,16,15,10],[0,0,7,4,4,9],[3,3,7,7,7,9],[6,6,7,10,10,9],[9,9,7,13,13,9],[12,12,7,16,16,9]],["B"],1)
parts["wheel"]=("Koło rusztowania","Caster wheel",[[5,0,5,11,2,11],[3,2,4,13,8,12],[5,8,5,11,13,11]],["B"],1)
parts["wheel_locked"]=("Koło z hamulcem","Locked caster",[[5,0,5,11,2,11],[3,2,4,13,8,12],[5,8,5,11,13,11],[10,8,4,15,10,8]],["B"],1)
parts["safety_net"]=("Siatka ochronna","Safety net",[[0,0,7,1,16,8],[3.5,0,7,4.5,16,8],[7,0,7,8,16,8],[10.5,0,7,11.5,16,8],[14,0,7,15,16,8],[0,0,7,16,1,8],[0,3.5,7,16,4.5,8],[0,7,7,16,8,8],[0,10.5,7,16,11.5,8],[0,14,7,16,15,8]],["B"],1)
parts["coupler"]=("Złącze krzyżowe","Cross coupler",[[3,6,6,13,10,10],[6,3,5,10,13,11]],["B"],1)
parents={"standard_half":"standard","standard_top":"standard","standard_heavy":"standard","standard_extension":"standard","ledger_half":"ledger","ledger_double":"ledger","ledger_heavy":"ledger","ledger_end":"ledger","brace_reverse":"brace","brace_cross":"brace","deck_half":"deck","deck_wood":"deck","deck_hatch":"deck","base_high":"base_jack","base_low":"base_jack","ladder_wide":"ladder","ladder_narrow":"ladder","guardrail_single":"guardrail","toe_board":"guardrail","frame":"standard","frame_cross":"frame","wheel":"base_jack","wheel_locked":"wheel","safety_net":"guardrail","coupler":"ledger"}
def write(path,data):
 p=root/path
 p.parent.mkdir(parents=True,exist_ok=True)
 p.write_text(json.dumps(data,ensure_ascii=False,indent=2),encoding="utf-8")
a=f"assets/{mod}"
d=f"data/{mod}"
pl={"itemGroup.scaffolding_ultra":"SCAFFOLDING ULTRA"}
en=dict(pl)
for name,(pol,eng,boxes,pattern,count) in parts.items():
 pl[f"block.{mod}.{name}"]=pol
 en[f"block.{mod}.{name}"]=eng
 elements=[{"from":b[:3],"to":b[3:],"faces":{face:{"texture":"#steel"} for face in ["north","south","east","west","up","down"]}} for b in boxes]
 write(f"{a}/models/block/{name}.json",{"parent":"minecraft:block/block","textures":{"particle":f"{mod}:block/steel","steel":"minecraft:block/oak_planks" if name=="deck_wood" else f"{mod}:block/steel"},"elements":elements})
 write(f"{a}/models/item/{name}.json",{"parent":f"{mod}:block/{name}"})
 write(f"{a}/blockstates/{name}.json",{"variants":{f"facing={f}":{"model":f"{mod}:block/{name}","y":y} for f,y in [("north",0),("east",90),("south",180),("west",270)]}})
 key={"I":{"item":"minecraft:iron_ingot"}}
 if name in parents:
  pattern=["BI"]
  key={"B":{"item":f"{mod}:{parents[name]}"},"I":{"item":"minecraft:iron_nugget"}}
 if name=="deck_wood":
  pattern=["BW"]
  key={"B":{"item":f"{mod}:deck"},"W":{"item":"minecraft:oak_planks"}}
 if any("N" in row for row in pattern):key["N"]={"item":"minecraft:iron_nugget"}
 write(f"{d}/recipe/{name}.json",{"type":"minecraft:crafting_shaped","category":"building","pattern":pattern,"key":key,"result":{"id":f"{mod}:{name}","count":count}})
 write(f"{d}/loot_table/blocks/{name}.json",{"type":"minecraft:block","pools":[{"rolls":1,"entries":[{"type":"minecraft:item","name":f"{mod}:{name}"}],"conditions":[{"condition":"minecraft:survives_explosion"}]}]})
for tool,pol,eng,pattern in [("hammer","Młotek monterski","Assembly hammer",["II","SI","S "]),("wrench","Klucz monterski","Assembly wrench",["I I"," I "," I "])]:
 pl[f"item.{mod}.{tool}"]=pol
 en[f"item.{mod}.{tool}"]=eng
 write(f"{a}/models/item/{tool}.json",{"parent":"minecraft:item/handheld","textures":{"layer0":"minecraft:item/iron_pickaxe" if tool=="hammer" else "minecraft:item/iron_hoe"}})
 key={"I":{"item":"minecraft:iron_ingot"}}
 if any("S" in row for row in pattern):key["S"]={"item":"minecraft:stick"}
 write(f"{d}/recipe/{tool}.json",{"type":"minecraft:crafting_shaped","category":"equipment","pattern":pattern,"key":key,"result":{"id":f"{mod}:{tool}","count":1}})
write("data/minecraft/tags/block/climbable.json",{"replace":False,"values":[f"{mod}:{name}" for name in parts if name.startswith("ladder")]})
write(f"{a}/lang/pl_pl.json",pl)
write(f"{a}/lang/en_us.json",en)
write("data/minecraft/tags/block/mineable/pickaxe.json",{"replace":False,"values":[f"{mod}:{name}" for name in parts]})
write("data/minecraft/tags/block/needs_stone_tool.json",{"replace":False,"values":[f"{mod}:{name}" for name in parts]})
rng=random.Random(21)
raw=b""
for y in range(16):
 raw+=b"\x00"
 for x in range(16):
  v=max(0,min(255,170+rng.randint(-13,13)+(8 if x%5==0 else 0)))
  raw+=bytes((v,v+3,v+7,255))
def chunk(t,b):return struct.pack(">I",len(b))+t+b+struct.pack(">I",zlib.crc32(t+b)&0xffffffff)
p=root/f"{a}/textures/block/steel.png"
p.parent.mkdir(parents=True,exist_ok=True)
p.write_bytes(b"\x89PNG\r\n\x1a\n"+chunk(b"IHDR",struct.pack(">IIBBBBB",16,16,8,6,0,0,0))+chunk(b"IDAT",zlib.compress(raw))+chunk(b"IEND",b""))
print("Generated 33 block models, recipes, loot tables and steel texture")
